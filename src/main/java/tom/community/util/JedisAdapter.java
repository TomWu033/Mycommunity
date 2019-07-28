package tom.community.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.*;
import tom.community.model.User;

public class JedisAdapter {
    public static void print(int index,Object obj){
        System.out.println(String.format("%d,%s",index,obj.toString()));
    }

    public static void main(String[] args) {
        Jedis jedis=new Jedis("redis://localhost:6379/1");//默认6379端口
        jedis.flushDB();//删除，flushAll删除全部
        //get、set
        jedis.set("hello","world");
        print(1,jedis.get("hello"));
        jedis.rename("hello","newhello");
        print(1,jedis.get("newhello"));
        jedis.setex("hello2",15,"world");//设置超时时间15s

        //数值
        jedis.set("pv","100");
        jedis.incr("pv");//加1
        jedis.incrBy("pv",5);//+5
        print(2,jedis.get("pv"));
        jedis.decrBy("pv",2);
        print(2,jedis.get("pv"));

        //list操作链表
        String listname="list";
        jedis.del(listname);
        for (int i=0;i<10;i++){
            jedis.lpush(listname,"a"+String.valueOf(i));
        }
        print(3,jedis.lrange(listname,0,12));//从0到12全部取出，两边都是闭区间
        print(4,jedis.llen(listname));//长度
        print(5,jedis.lpop(listname));//栈的弹出
        print(6,jedis.lindex(listname,3));//直接取元素
        print(7,jedis.linsert(listname,ListPosition.AFTER,"a4","xx"));//随机插入
        print(8,jedis.linsert(listname,ListPosition.BEFORE,"a4","bb"));
        print(3,jedis.lrange(listname,0,12));

        //hash
        String userKey="userxx";
        jedis.hset(userKey,"name","jim");
        jedis.hset(userKey,"age","12");
        jedis.hset(userKey,"phone","18618181818");
        print(12,jedis.hget(userKey,"name"));
        print(13,jedis.hgetAll(userKey));
        jedis.hdel(userKey,"phone");
        print(14,jedis.hgetAll(userKey));
        print(15,jedis.hexists(userKey,"email"));
        print(16,jedis.hexists(userKey,"age"));
        print(17,jedis.hkeys(userKey));
        print(18,jedis.hvals(userKey));
        jedis.hsetnx(userKey,"school","zju");//不存在时设置
        jedis.hsetnx(userKey,"name","yxy");//不存在时设置
        print(19,jedis.hgetAll(userKey));

        //set集合
        String likeKey1="commentLike1";
        String likeKey2="commentLike2";
        for (int i=0;i<10;i++){
            jedis.sadd(likeKey1,String.valueOf(i));
            jedis.sadd(likeKey2,String.valueOf(i*i));
        }
        print(20,jedis.smembers(likeKey1));
        print(21,jedis.smembers(likeKey2));
        print(22,jedis.sunion(likeKey1,likeKey2));//求并，且集合自动去重
        print(23,jedis.sdiff(likeKey1,likeKey2));//我有你没有的
        print(24,jedis.sinter(likeKey1,likeKey2));//求交
        print(25,jedis.sismember(likeKey1,"12"));//成员是否存在
        jedis.srem(likeKey1,"5");//删除
        print(26,jedis.smembers(likeKey1));
        jedis.smove(likeKey2,likeKey1,"25");//移动元素过来
        print(27,jedis.smembers(likeKey1));
        print(29,jedis.scard(likeKey1));//统计多少人

        //优先队列
        String rank="rankKey";
        jedis.zadd(rank,80,"Jim");
        jedis.zadd(rank,83,"Lucy");
        jedis.zadd(rank,79,"Kris");
        jedis.zadd(rank,99,"Tom");
        jedis.zadd(rank,25,"Mei");
        jedis.zadd(rank,15,"Cxk");
        print(30,jedis.zcard(rank));//总数
        print(31,jedis.zcount(rank,61,100));//区间人数
        print(32,jedis.zscore(rank,"Lucy"));
        jedis.zincrby(rank,2,"Luc");//没有就0再加
        print(36,jedis.zrange(rank,1,3));//低分
        print(36,jedis.zrevrange(rank,1,3));
        for (Tuple tuple:jedis.zrangeByScoreWithScores(rank,"60","100")){
            print(37,tuple.getElement()+":"+String.valueOf(tuple.getScore()));//取分值范围内的
        }
        print(38,jedis.zrank(rank,"Tom"));
        print(38,jedis.zrevrank(rank,"Tom"));
        String setKey="zset";
        jedis.zadd(setKey,1,"a");
        jedis.zadd(setKey,1,"b");
        jedis.zadd(setKey,1,"c");
        jedis.zadd(setKey,1,"d");
        jedis.zadd(setKey,1,"e");
        print(40,jedis.zlexcount(setKey,"-","+"));//负无穷到正无穷
        print(40,jedis.zlexcount(setKey,"[b","[d"));//bd区间，全闭
        jedis.zremrangeByLex(setKey,"(c","+");//按字典序删除c以上的
        print(44,jedis.zrange(setKey,0,10));

        //redis连接池,默认8个
        JedisPool pool=new JedisPool();
        for (int i=0;i<100;++i){
            Jedis j=pool.getResource();
            print(45,j.get("pv"));
            j.close();
        }

        //redis做缓存
        User user=new User();
        user.setName("xx");
        user.setAvatarUrl("ppp");
        user.setSalt("salt ");
        user.setId(1L);
        print(46,JSONObject.toJSONString(user));
        jedis.set("user1",JSONObject.toJSONString(user));

        String value=jedis.get("user");
        User user1 = JSON.parseObject(value,User.class);
        print(47,user1);


    }
}
