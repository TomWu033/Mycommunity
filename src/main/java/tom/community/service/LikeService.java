package tom.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tom.community.enums.CommentTypeEnum;
import tom.community.util.RedisKeyUtil;

@Service
public class LikeService {
    @Autowired
    private JedisService jedisService;

    public long like(long userId, CommentTypeEnum type, long id){
        String likeKey= RedisKeyUtil.getLikeKey(type,id);
        jedisService.sadd(likeKey,String.valueOf(userId));

        String disLikeKey=RedisKeyUtil.getDislikeKey(type, id);
        jedisService.srem(disLikeKey,String.valueOf(userId));

        return jedisService.scard(likeKey);
    }

    public long dislike(long userId, CommentTypeEnum type, long id){
        String disLikeKey= RedisKeyUtil.getDislikeKey(type,id);
        jedisService.sadd(disLikeKey,String.valueOf(userId));

        String likeKey=RedisKeyUtil.getLikeKey(type, id);
        jedisService.srem(likeKey,String.valueOf(userId));

        return jedisService.scard(likeKey);
    }

    public int getLikeStatus(long userId, CommentTypeEnum type, long id){
        String likeKey=RedisKeyUtil.getLikeKey(type, id);
        if (jedisService.sismember(likeKey,String.valueOf(userId))){
            return 1;//已经点过喜欢
        }
        String dislikeKey=RedisKeyUtil.getDislikeKey(type, id);
        return jedisService.sismember(dislikeKey,String.valueOf(userId))?-1:0;//0表示既没有喜欢也没有不喜欢
    }

    public long getLikeCount(CommentTypeEnum type, long id){
        String likeKey=RedisKeyUtil.getLikeKey(type, id);
        return jedisService.scard(likeKey);
    }
}
