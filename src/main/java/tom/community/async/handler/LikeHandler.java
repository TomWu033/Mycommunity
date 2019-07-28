package tom.community.async.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tom.community.async.EventHandler;
import tom.community.async.EventModel;
import tom.community.async.EventType;
import tom.community.enums.NotificationTypeEnum;
import tom.community.model.Comment;
import tom.community.model.Notification;
import tom.community.service.CommentService;
import tom.community.service.NotificationService;
import tom.community.service.UserService;
import tom.community.util.JUtil;

import java.util.Arrays;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

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
        long entityId = eventModel.getEntityId();
        Comment comment=commentService.getComment(entityId);
        msg.setOuterid(comment.getParentId());
        msg.setType(EventType.LIKE.getValue());
        msg.setNotifierName(userService.getUser(eventModel.getActorId()));
        msg.setOuterTitle(eventModel.getExt("questionTitle"));
        msg.setStatus(0);
        notificationService.addNotify(msg);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
