package com.focusedu;

import com.focusedu.utils.RedisUtils;
import com.focusedu.utils.lang.StringUtils;
import com.focusedu.utils.sms.SmsManager;
import com.focusedu.utils.sms.SmsResult;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * RedisTest
 *
 * @author liuruichao
 * @date 15/9/12 下午2:13
 */
public class RedisTest {
    private class TopEntity implements Serializable {
        private String title;
        private String ccCode;

        public TopEntity(String title, String ccCode) {
            this.title = title;
            this.ccCode = ccCode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCcCode() {
            return ccCode;
        }

        public void setCcCode(String ccCode) {
            this.ccCode = ccCode;
        }
    }

    @Test
    public void test() {
        List<Object> list = Collections.singletonList("liuruichao");
        System.out.println(list.size());
    }

//    @Test
//    public void query() {
//        List<TopEntity> result = new ArrayList<>();
//        RedisUtils redisUtils = new RedisUtils("121.41.49.105", 6379);
//        Set<String> keys = redisUtils.keys("*TPO*");
//        Gson gson = new Gson();
//        for (String key : keys) {
//            Object obj = redisUtils.get(key);
//            if (obj instanceof ArrayList) {
//                ArrayList list = (ArrayList) obj;
//                for (Object str : list) {
//                    String[] attri = str.toString().split(":");
//                    result.add(new TopEntity(attri[0], attri[1]));
//                }
//            }
//        }
//        System.out.println(gson.toJson(result));
//    }

//    @Test
//    public void smsTest() throws Exception {
//        String randomCode = StringUtils.randomStr(6, true);
//        String content = "您的验证码是：" + randomCode + "。请不要把验证码泄露给其他人。";
//        SmsResult smsResult = SmsManager.sendMessage("15910591399", content);
//        if (smsResult.getCode() == 2) {
//            System.out.println("send sms success");
//        } else {
//            System.out.println("发送失败：" + smsResult.getMsg());
//        }
//    }

    //@Test
    //public void test() {
    //    RedisUtils redisUtils = new RedisUtils("121.41.49.105", 6379);
    //    redisUtils.select(1);
    //    List<Integer> list = (List) redisUtils.get("memberType_119");
    //    //for (Integer n : list) {
    //    //    System.out.println(n);
    //    //}
    //    redisUtils.set("memberType_118", list);
    //}

//    private RedisUtils redisUtils;
//
//    @Before
//    public void before() throws IOException {
//        redisUtils = new RedisUtils("redis.properties");
//        redisUtils.select(3);
//    }
//
//    @Test
//    public void testKeys() {
//        System.out.println(redisUtils.keys().size());
//    }
//
//    @Test
//    public void testQuery() {
//        Object value = redisUtils.get("name");
//        System.out.println(value);
//    }
//
//    @Test
//    public void testDel() {
//        long result = redisUtils.remove("name");
//        System.out.println(result);
//    }
//
//    @Test
//    public void testSave() {
//        String name = "liuruichao";
//        redisUtils.set("name", "liuruichao");
//    }
}
