package com.xzm.redis;

import com.wandoulabs.jodis.JedisResourcePool;
import com.wandoulabs.jodis.RoundRobinJedisPool;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

import java.security.Key;
import java.util.List;

/**
 * Created by Bradish7Y on 16/1/7.
 */
public class JedisTest {

    private JedisResourcePool jedisPool;
    private final static String KEY = "name";

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("redis/applicationContext.xml");
    }

    @Before
    public void before() {
        jedisPool = RoundRobinJedisPool.create()
                .curatorClient("192.168.1.82:2181", 30000).zkProxyDir("/zk/codis/db_test/proxy").build();

    }

    /**
     * 测试和key相关的命令
     */
    @Test
    public void testKey() {

        try (Jedis jedis = jedisPool.getResource()) {
            System.out.print("set " + KEY + ":ronaldo");
            String replyStr = jedis.set(KEY, "ronaldo");
            System.out.println(", reply=" + replyStr);

            replyStr = jedis.get(KEY);
            System.out.println("get " + KEY + ":" + replyStr);

            System.out.print("del " + KEY);
            long replyInt = jedis.del(KEY);
            System.out.println(", reply=" + replyInt);

        }
    }

    @Test
    public void testString() {

    }

    @Test
    public void testList() {

    }

    @Test
    public void testHashMap() {

    }

    @Test
    public void testSet() {

    }

    @Test
    public void testSortedSet() {

    }


    @Test
    public void testAnything() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.mset("name1", "x", "name2", "z", "name3", "m");
            List<String> names = jedis.mget("name1", "name2", "name3");
            for (String s : names) {
                System.out.println("s = " + s);
            }

            jedis.set("name", "ronaldo", "xx", "ex", 10);
        }
    }

    /**
     * 10秒内连接数超过10个,则提示限速
     */
    @Test
    public void testLimitRateWithLuaScript() {
        String luaString = "local current\n" +
                "current = redis.call(\"incr\",KEYS[1])\n" +
                "redis.log(redis.LOG_NOTICE, current)\n" +
                "\n" +
                "if current ~= nil and tonumber(current) > 10 then\n" +
                "    return \"access deny\"\n" +
                "end\n" +
                "\n" +
                "if current ~= nil and tonumber(current) == 1 then\n" +
                "    redis.call(\"expire\",KEYS[1],10)\n" +
                "end\n" +
                "\n" +
                "\n" +
                "return \"conns:\" .. current\n";


        try (Jedis jedis = jedisPool.getResource()) {
            //代理不支持缓存luascript
            //String str = jedis.scriptLoad(luaString) ;

            for (int i = 0; i < 15; i++) {

                Object obj = jedis.eval(luaString, 1, "ip:limit");

                if (obj != null) {
                    System.out.println("obj = " + obj);
                }
            }

        }


    }


}
