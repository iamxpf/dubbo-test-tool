package com.xpf.dubbo.dubbotool.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xpf.dubbo.dubbotool.constant.Const;
import com.xpf.dubbo.dubbotool.dto.ResultDTO;
import com.xpf.dubbo.dubbotool.service.IZookeeperService;
import com.xpf.dubbo.dubbotool.util.RedisUtil;
import com.xpf.dubbo.dubbotool.vo.RegistryVO;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
@Service
public class ZookeeperServiceImpl implements IZookeeperService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultDTO<RegistryVO> addRegistry(RegistryVO vo) {
        boolean exit = redisUtil.hHasKey(Const.REGISTRY_KEY, vo.getName());
        if (exit) {
            return ResultDTO.createError(vo.getName() + " has exit.", vo);
        }
        redisUtil.hset(Const.REGISTRY_KEY, vo.getName(), vo.getUrl());
        return ResultDTO.createSuccess(vo.getName() + " add success.", vo);
    }

    @Override
    public ResultDTO<RegistryVO> delRegistry(RegistryVO vo) {
        boolean success = redisUtil.hdel(Const.REGISTRY_KEY, vo.getName()).intValue() > 0;
        if (success) {
            return ResultDTO.createSuccess(vo.getName() + " delete success.", vo);
        }
        return ResultDTO.createError(vo.getName() + " maybe not exit,error", vo);

    }

    @Override
    public List<RegistryVO> listRegistry() {
        List<RegistryVO> result = new ArrayList<>();
        Set<Object> list = redisUtil.hkeys(Const.REGISTRY_KEY);
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.stream().map(o -> {
                RegistryVO vo = new RegistryVO();
                String name = String.valueOf(o);
                String url = String.valueOf(redisUtil.hget(Const.REGISTRY_KEY, name));
                vo.setName(String.valueOf(o));
                vo.setUrl(url);
                return vo;
            }).collect(Collectors.toList());
        }
        return result;
    }
}
