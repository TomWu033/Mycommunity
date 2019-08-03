package tom.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import tom.community.util.RedisKeyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    @Autowired
    private JedisService jedisService;

    public boolean follow(long userId,int entityType,long entityId){
        String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        long gmtCreate=System.currentTimeMillis();//关注时间
        Jedis jedis= jedisService.getJedis();
        Transaction tx= jedisService.multi(jedis);
        tx.zadd(followerKey,gmtCreate,String.valueOf(userId));
        tx.zadd(followeeKey,gmtCreate,String.valueOf(entityId));
        List<Object> ret= jedisService.exec(tx,jedis);
        return ret.size()==2&&(Long)ret.get(0)>0&&(Long)ret.get(1)>0;
    }
    //取消关注
    public boolean unfollow(long userId,int entityType,long entityId){
        String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        Jedis jedis= jedisService.getJedis();
        Transaction tx= jedisService.multi(jedis);
        tx.zrem(followerKey,String.valueOf(userId));
        tx.zrem(followeeKey,String.valueOf(entityId));
        List<Object> ret= jedisService.exec(tx,jedis);
        return ret.size()==2&&(Long)ret.get(0)>0&&(Long)ret.get(1)>0;
    }
    //获取粉丝
    private List<Long> getIdsFromSet(Set<String> idset){
        List<Long> ids=new ArrayList<>();
        for (String str:idset){
            ids.add(Long.parseLong(str));
        }
        return ids;
    }
    public List<Long> getFollowers(int entityType,long entityId,int count){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisService.zrevrange(followerKey,0,count));
    }
    //带翻页的
    public List<Long> getFollowers(int entityType,long entityId,int offset,int count){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisService.zrevrange(followerKey,offset,count));
    }
    //获取关注对象
    public List<Long> getFollowees(int entityType,long entityId,int count){
        String followeeKey=RedisKeyUtil.getFolloweeKey(entityId,entityType);
        return getIdsFromSet(jedisService.zrevrange(followeeKey,0,count));
    }
    //带翻页的
    public List<Long> getFollowees(int entityType,long entityId,int offset,int count){
        String followeeKey=RedisKeyUtil.getFolloweeKey(entityId,entityType);
        return getIdsFromSet(jedisService.zrevrange(followeeKey,offset,count));
    }
    //获取关注对象、粉丝总数
    public long getFollowerCount(int entityType,long entityId){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisService.zcard(followerKey);
    }
    public long getFolloweeCount(int entityType,long entityId){
        String followeeKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisService.zcard(followeeKey);
    }
    //是不是粉丝
    public boolean isFollower(long userId,int entityType,long entityId){
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisService.zscore(followerKey,String.valueOf(userId))!=null;
    }
}
