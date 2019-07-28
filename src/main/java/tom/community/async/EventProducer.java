package tom.community.async;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tom.community.service.JedisService;
import tom.community.util.RedisKeyUtil;

/**
 * 统一的事件队列
 */
@Service
public class EventProducer {
    @Autowired
    private JedisService jedisService;

    public boolean fireEvent(EventModel eventModel){
        try{
            String json= JSONObject.toJSONString(eventModel);//转换成json字符串
            String key= RedisKeyUtil.getEventQueueKey();
            jedisService.lpush(key,json);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
