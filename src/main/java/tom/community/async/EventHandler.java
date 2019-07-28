package tom.community.async;

import java.util.List;

public interface EventHandler {
    void doHandle(EventModel eventModel);//处理event
    List<EventType> getSupportEventTypes();//注册自己关心的event
}
