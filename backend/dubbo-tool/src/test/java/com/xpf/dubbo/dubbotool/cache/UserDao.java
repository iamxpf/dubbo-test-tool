package com.xpf.dubbo.dubbotool.cache;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

/**
 * @author: xia.pengfei
 * @date: 2020/6/28
 * @taskId:
 */
@Component
@EnableCaching
public class UserDao {

        private Map<Long, User> userMap;

        @PostConstruct
        public void init() {
            //模拟数据库
            userMap = new HashMap<Long, User>();
            userMap.put(1L, new User(1L,"micro1"));
            userMap.put(2L, new User(2L, "micro2"));
        }

        @Cacheable("user")  // 注解key属性可以执行缓存对象user(可以理解为一个map)的key
        public User getUser(Long userId) {
            System.out.println("查询数据库:userId ->" + userId);
            return userMap.get(userId);
        }

        @Cacheable(value = "nameCache", key = "#name")
        public User getUserByName(Long userId, String name) {
            System.out.println("查询数据库:userId ->" + userId);
            return userMap.get(userId);
        }

        @Cacheable("nameCache")
        public User getUserByName(String name) {
            System.out.println("查询数据库:userName : " + name);
            for (Long k : userMap.keySet()) {
                if (userMap.get(k).equals(name)) {
                    return userMap.get(k);
                }
            }
            return null;
        }

        @CachePut("user") // 与Cacheable区别就是Cacheable先看缓存如果有，直接缓存换回，CachePut则是每次都会调用并且把返回值放到缓存
        public User getUser2(Long userId) {
            System.out.println("查询数据库:userId : " + userId);
            return userMap.get(userId);
        }

        @CacheEvict("user")
        public void removeFromCache(Long userId) {
            return ;
        }

}
