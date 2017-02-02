package com.healist.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by Healist on 2017/1/31.
 */
public class TestRedis {
    @Test
    public void testRedis() {
        Jedis jedis = new Jedis("localhost");
        //jedis.set("tutorial-name", "Redis tutorial");
        System.out.println("Server is running:" + jedis.get("c11985e962bf4fc6a8e239efcb337cbc"));
    }

}
