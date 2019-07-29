package tom.community.async.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tom.community.async.EventHandler;
import tom.community.async.EventModel;
import tom.community.async.EventType;
import tom.community.enums.EntityType;
import tom.community.model.Comment;
import tom.community.model.Notification;
import tom.community.model.Question;
import tom.community.service.CommentService;
import tom.community.service.NotificationService;
import tom.community.service.QuestionService;
import tom.community.service.UserService;

import java.util.Arrays;
import java.util.List;

@Component
public class FollowHandler implements EventHandler {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Override
    public void doHandle(EventModel eventModel) {
        Long entityOwnerId = eventModel.getEntityOwnerId();
        if (entityOwnerId.equals(eventModel.getActorId())) {
            return;
        }
        Notification msg=new Notification();
        msg.setNotifier(eventModel.getActorId());
        msg.setReceiver(entityOwnerId);
        msg.setGmtCreate(System.currentTimeMillis());
        msg.setStatus(0);
        msg.setType(EventType.FOLLOW.getValue());
        msg.setNotifierName(userService.getUserName(eventModel.getActorId()));
        Integer type=eventModel.getEntityType();
        if (type.equals(EntityType.ENTITY_USER)){
            //用户id
            long entityId = eventModel.getEntityId();
            msg.setOuterid(entityId);
            msg.setOuterTitle("你");
        }
        if (type.equals(EntityType.ENTITY_QUESTION)){
            //题目id
            Question question=questionService.getQuestion(eventModel.getEntityId());
            msg.setOuterid(eventModel.getEntityId());
            msg.setOuterTitle(question.getTitle());
        }
        notificationService.addNotify(msg);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
