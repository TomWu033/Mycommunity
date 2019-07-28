package tom.community.async;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
    private EventType type;//事件类型(评论）
    private long actorId;//触发者（谁评论了）
    private int entityType;//触发的实体类型（评论了什么）
    private long entityId;
    private long entityOwnerId;

    private Map<String,String> exts=new HashMap<>();

    public EventModel(){}

    public EventModel setExt(String key, String value){
        exts.put(key, value);
        return this;
    }

    public EventModel (EventType type){
        this.type=type;
    }

    public String getExt(String key){
        return exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public long getActorId() {
        return actorId;
    }

    public EventModel setActorId(long actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public long getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(long entityId) {
        this.entityId = entityId;
        return this;
    }

    public long getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(long entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
