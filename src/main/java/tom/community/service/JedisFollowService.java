package tom.community.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class JedisFollowService implements InitializingBean {
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/3");
    }

    public Jedis getJedis(){
        return pool.getResource();
    }
    //开启事务
    public Transaction multi(Jedis jedis){
        try{
            return jedis.multi();
        }catch (Exception e){
            log.error("关注功能事务异常"+e.getMessage());
        }
        return null;
    }
    //执行事务
    public List<Object> exec(Transaction tx,Jedis jedis){
        try{
            return tx.exec();
        }catch (Exception e){
            log.error("关注功能执行事务异常"+e.getMessage());
        }finally {
            if (tx!=null){
                try{
                    tx.close();
                }catch (Exception e){
                    log.error("关注功能事务关闭异常"+e.getMessage());
                }
            }
            if (jedis!=null){
                jedis.close();
            }
        }
        return null;
    }
    //加到集合里
    public long zadd(String key,long score,String value){
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.zadd(key,score,value);
        } catch (Exception e) {
            log.error("关注功能Zadd异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
    public Set<String> zrevrange(String key, int start, int end){
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.zrevrange(key,start,end);
        } catch (Exception e) {
            log.error("关注功能Zrange异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
    public long zcard(String key){
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);
        } catch (Exception e) {
            log.error("关注功能Zcard异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
    public Double zscore(String key,String member){
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.zscore(key,member);
        } catch (Exception e) {
            log.error("关注功能Zscore异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
