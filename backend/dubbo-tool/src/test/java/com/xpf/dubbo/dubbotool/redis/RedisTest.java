package com.xpf.dubbo.dubbotool.redis;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.xpf.dubbo.dubbotool.util.RedisUtil;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test() {
        String name = String.valueOf(redisUtil.get("name2"));
        System.out.println("-------------" + name);
    }

    private void printSet(Set set) {
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }
    }

}
