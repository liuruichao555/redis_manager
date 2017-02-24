package com.focusedu.serializer;

import com.focusedu.model.User;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * ProtobufRedisSerializer
 *
 * @author liuruichao
 * @date 15/11/7 下午10:30
 */
public class ProtobufRedisSerializer implements RedisSerializer<Object> {
    @Override

    public byte[] serialize(Object o) throws SerializationException {
        com.google.protobuf.GeneratedMessage msg = (GeneratedMessage) o;
        return msg.toByteArray();
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        User.user info = null;
        try {
            info = User.user.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static void main(String[] args) {
        ProtobufRedisSerializer serializer = new ProtobufRedisSerializer();

        User.user.Builder builder = User.user.newBuilder();
        builder.setId(1);
        builder.setUsername("liuruichao");
        builder.setPassword("liuruichao123");
        User.user user = builder.build();
        byte[] bytes = serializer.serialize(user);
        Jedis jedis = new Jedis("112.126.73.132", 6379);
        jedis.auth("liuruichao123");
        jedis.set("nicai".getBytes(), bytes);
    }
}
