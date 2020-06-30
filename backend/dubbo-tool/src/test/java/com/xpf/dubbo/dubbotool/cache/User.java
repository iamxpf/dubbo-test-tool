package com.xpf.dubbo.dubbotool.cache;

import java.io.Serializable;

/**
 * @author: xia.pengfei
 * @date: 2020/6/28
 * @taskId:
 */
public class User implements Serializable {

    private static final long serialVersionUID = 7314074844952567995L;

    private Long id;
    private String name;

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
