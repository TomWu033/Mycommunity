package tom.community.async;

import ch.qos.logback.core.pattern.color.RedCompositeConverter;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import tom.community.service.JedisService;
import tom.community.util.RedisKeyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    @Autowired
    private JedisService jedisService;

    private Map<EventType, List<EventHandler>> config=new HashMap<EventType, List<EventHandler>>();
    private ApplicationContext applicationContext;//上下文

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> beans=applicationContext.getBeansOfType(EventHandler.class);//找到应用程序里所有EventHandler实现类
        if (beans!=null){
            //
            for (Map.Entry<String,EventHandler> entry:beans.entrySet()){
                List<EventType> eventTypes=entry.getValue().getSupportEventTypes();//对每个实现类先找它关心什么Event
                for (EventType type:eventTypes){
                    //第一次就注册一下
                    if (!config.containsKey(type)){
                        config.put(type,new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());//关联起来
                }
            }
        }
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    String key= RedisKeyUtil.getEventQueueKey();
                    List<String> events=jedisService.brpop(0,key);//得到并去除list中最后一个元素，队列没有元素就会一直阻塞
                    for (String msg:events){
                        if (msg.equals(key)){
                            continue;//跳过第一个返回的key值
                        }
                        EventModel eventModel= JSON.parseObject(msg,EventModel.class);//把之间转成JSON的EventModel反序列化
                        //判断是否为非法事件
                        if (!config.containsKey(eventModel.getType())){
                            log.error("不能识别的事件类型");
                            continue;
                        }
                        for (EventHandler handler:config.get(eventModel.getType())){
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
