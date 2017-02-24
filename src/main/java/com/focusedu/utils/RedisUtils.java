package com.focusedu.utils;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

/**
 * RedisUtils
 *
 * @author liuruichao
 * @date 15/9/12 下午1:44
 */
public final class RedisUtils {
    private Jedis jedis;
    private JdkSerializationRedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
    private StringRedisSerializer keySerializer = new StringRedisSerializer();
    private int curDbIndex = 0;

    public RedisUtils(String host, int port) {
        jedis = new Jedis(host, port, 10000);
        if (host.equals("192.168.3.100")) {
            jedis.auth("focusedu123");
        }
    }

    public RedisUtils(String host, int port, String password) {
        jedis = new Jedis(host, port, 10000);
        jedis.auth(password);
    }

    /**
     * 删除redis记录，可以用正则，返回删除数量
     * @param key
     * @return
     */
    public long remove(String key) {
        long result = 0;
        connect();

        Set<String> keys = jedis.keys(key);
        String[] arr = new String[keys.size()];
        Iterator<String> iterator = keys.iterator();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = iterator.next();
        }
        if (arr.length > 0) {
            result = jedis.del(arr);
        }
        return result;
    }

    /**
     * 获取数据，没有返回为null
     * @param key
     * @return
     */
    public Object get(String key) {
        connect();
        try {
            return valueSerializer.deserialize(jedis.get(keySerializer.serialize(key)));
        } catch (Exception e) {
            return jedis.get(key);
        }
    }

    /**
     * 添加数据
     * @param key
     * @param obj
     */
    public void set(String key, Object obj) {
        byte[] value = valueSerializer.serialize(obj);
        jedis.set(keySerializer.serialize(key), value);
    }

    /**
     * 选择数据库
     * @param dbIndex
     * @return
     */
    public int select(int dbIndex) {
        curDbIndex = dbIndex;
        jedis.select(curDbIndex);
        return curDbIndex;
    }

    /**
     * 当前index,数据库所有key
     * @return
     */
    public Set<String> keys() {
        Set<String> keys = jedis.keys("*");
        return keys;
    }

    public Set<String> keys(String key) {
        Set<String> keys = jedis.keys("*" + key +"*");
        return keys;
    }

    public void destroy() {
        jedis.close();
    }

    public long getDbSize() {
        return jedis.dbSize();
    }

    private void connect() {
        if (!jedis.isConnected()) {
            jedis.connect();
        }
    }
}
