package tom.community.util;

import tom.community.enums.CommentTypeEnum;

public class RedisKeyUtil {
    private static String SPILT=":";
    private static String BIZ_LIKE="LIKE";
    private static String BIZ_DISLIKE="DISLIKE";
    private static String BIZ_EVENTQUEUE="EVENT_QUEUE";
    //粉丝
    private static String BIZ_FOLLOWER="FOLLOWER";
    //关注对象
    private static String BIZ_FOLLOWEE="FOLLOWEE";

    public static String getLikeKey(CommentTypeEnum type, long id){
        return BIZ_LIKE+SPILT+String.valueOf(type)+SPILT+String.valueOf(id);
    }
    public static String getDislikeKey(CommentTypeEnum type, long id){
        return BIZ_DISLIKE+SPILT+String.valueOf(type)+SPILT+String.valueOf(id);
    }
    public static String getEventQueueKey(){
        return BIZ_EVENTQUEUE;
    }
    //每一个实体粉丝的key
    public static String getFollowerKey(int entityType,long entityId){
        return BIZ_FOLLOWER+SPILT+String.valueOf(entityType)+SPILT+String.valueOf(entityId);
    }
    //某一个用户关注某一类实体的key'
    public static String getFolloweeKey(long entityId,int entityType){
        return BIZ_FOLLOWER+SPILT+String.valueOf(entityId)+SPILT+String.valueOf(entityType);
    }
}
