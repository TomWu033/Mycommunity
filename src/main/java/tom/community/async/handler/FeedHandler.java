package tom.community.async.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tom.community.async.EventHandler;
import tom.community.async.EventModel;
import tom.community.async.EventType;
import tom.community.enums.EntityType;
import tom.community.exception.CustomizeErrorCode;
import tom.community.exception.CustomizeException;
import tom.community.model.Feed;
import tom.community.model.Notification;
import tom.community.model.Question;
import tom.community.model.User;
import tom.community.service.*;
import tom.community.util.RedisKeyUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FeedHandler implements EventHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private FeedService feedService;
    @Autowired
    private FollowService followService;
    @Autowired
    private JedisService jedisService;

    @Override
    public void doHandle(EventModel eventModel) {
        Feed feed=new Feed();
        feed.setGmtCreate(System.currentTimeMillis());
        feed.setUserId(eventModel.getActorId());//谁触发的
        feed.setType(eventModel.getType().getValue());
        feed.setData(buildFeedData(eventModel));
        if (feed.getData()==null){
            return;
        }
        feedService.addFeed(feed);
        //给事件的粉丝推
        List<Long> followers=followService.getFollowers(EntityType.ENTITY_USER,eventModel.getActorId(),Integer.MAX_VALUE);
        for (long follower:followers){
            String timelineKey= RedisKeyUtil.getTimelineKey(follower);
            jedisService.lpush(timelineKey,String.valueOf(feed.getId()));
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.REPLY_QUESTION,EventType.FOLLOW});
    }

    private String buildFeedData(EventModel model){
        Map<String,String> map=new HashMap<>();
        User actor=userService.getUser(model.getActorId());
        if (actor==null){
            return null;
        }
        map.put("userId",String.valueOf(actor.getId()));
        map.put("avatarUrl",actor.getAvatarUrl());
        map.put("userName",actor.getName());
        if (model.getType()==EventType.REPLY_QUESTION||
                (model.getType()==EventType.FOLLOW&&model.getEntityType()==EntityType.ENTITY_QUESTION)){
            Question q=questionService.getQuestion(model.getEntityId());
            if (q==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            map.put("questionId",String.valueOf(q.getId()));
            map.put("questionName",q.getTitle());
            return JSONObject.toJSONString(map);//返回json对象
        }
        return null;
    }
}
