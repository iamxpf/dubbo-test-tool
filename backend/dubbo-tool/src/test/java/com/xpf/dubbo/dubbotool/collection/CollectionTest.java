package com.xpf.dubbo.dubbotool.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

/**
 * @author: xia.pengfei
 * @date: 2020/6/30
 * @taskId:
 */
public class CollectionTest {

    @Test
    public void test() {
        List<String> lookup = new ArrayList<>();
        lookup.add("aa,bb,cc,dd,ee");
        lookup.add("cc,ddd,e,ff,gg");
        Set<String> methodSet = new TreeSet<>();
        lookup.stream().map(e -> {
            String methods = e;
            System.out.println(e);
            List list = CollectionUtils.arrayToList(methods.split(","));
            methodSet.addAll(list);
            return e;
        });
        List<String> list = new ArrayList<>();
        list.addAll(methodSet);
    }

}
