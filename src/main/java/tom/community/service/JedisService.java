package tom.community.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class JedisService implements InitializingBean {
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/2");
    }

    public long sadd(String key, String value) {
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
    public long srem(String key, String value) {
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key){
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key,String value){
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key,value);
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public long lpush(String key, String json) {
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, json);
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> brpop(int i, String key) {
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(i,key);
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return new ArrayList<>();
    }

    public List<String> lrange(String timelineKey, int i, int i1) {
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(timelineKey,i,i1);
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return new ArrayList<>();
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
