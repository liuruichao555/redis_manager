package com.focusedu;

import redis.clients.jedis.Jedis;

/**
 * Test2
 *
 * @author liuruichao
 * Created on 2015-12-25 15:57
 */
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("112.126.73.132", 6379);
        jedis.auth("liuruichao123");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    jedis.publish("demo1", "liuruichao_" + j);
                }
            }).start();
        }
        Thread.sleep(50000);
    }
}