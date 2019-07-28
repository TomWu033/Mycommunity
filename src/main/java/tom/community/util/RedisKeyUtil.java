package tom.community.util;

import tom.community.enums.CommentTypeEnum;

public class RedisKeyUtil {
    private static String SPILT=":";
    private static String BIZ_LIKE="LIKE";
    private static String BIZ_DISLIKE="DISLIKE";
    private static String BIZ_EVENTQUEUE="EVENT_QUEUE";

    public static String getLikeKey(CommentTypeEnum type, long id){
        return BIZ_LIKE+SPILT+String.valueOf(type)+SPILT+String.valueOf(id);
    }
    public static String getDislikeKey(CommentTypeEnum type, long id){
        return BIZ_DISLIKE+SPILT+String.valueOf(type)+SPILT+String.valueOf(id);
    }
    public static String getEventQueueKey(){
        return BIZ_EVENTQUEUE;
    }
}
