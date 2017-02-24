package com.focus_pay.shop.controller.dto;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * DelEmptyPermission
 *
 * @author liuruichao
 * @date 15/9/15 下午2:32
 */
public class DelEmptyPermission {
    public static void main(String[] args) {
        int total = 0;
        Jedis jedis = new Jedis("121.41.49.105", 6379);
        jedis.select(1);
        Set<String> keys = jedis.keys("*");
        JdkSerializationRedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                PermissionDto permissionDto = (PermissionDto) valueSerializer.deserialize(jedis.get(key.getBytes()));
                if (permissionDto != null) {
                    if (permissionDto.getGoodsIds() == null || permissionDto.getGoodsIds().size() <= 0) {
                        total ++;
                    }
                    if (permissionDto.getMemberDtos() == null || permissionDto.getMemberDtos().size() <= 0) {
                        total ++;
                    }
                }
            }
        }
        System.out.println(total);
    }
}
