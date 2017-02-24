package com.focusedu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Test * * @author liuruichao * @date 15/11/24 上午10:38
 */
public class Test {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("112.126.73.132", 6379);
        jedis.auth("liuruichao123");
        final MyJedisPubSub myJedisPubSub = new MyJedisPubSub();
        jedis.subscribe(myJedisPubSub, "demo1");

    }
    private static class MyJedisPubSub extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            System.out.println(String.format("update customer cusId : %s.", message));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            System.out.println(String.format("subscribe channel : %s, subscribeNum : %s.", channel, subscribedChannels));
        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
            System.out.println(String.format("unsubscribe channel : %s, subscribeNum : %s.", channel, subscribedChannels));
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
        }

        @Override
        public void onPUnsubscribe(String pattern, int subscribedChannels) {

        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {

        }
    }
}
