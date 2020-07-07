package com.xpf.dubbo.dubbotool.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistry;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperClient;
import com.alibaba.dubbo.remoting.zookeeper.curator.CuratorZookeeperTransporter;
import com.xpf.dubbo.dubbotool.annotation.SelLog;
import com.xpf.dubbo.dubbotool.dto.ResultDTO;
import com.xpf.dubbo.dubbotool.service.IDubboService;
import com.xpf.dubbo.dubbotool.vo.AchieveVO;
import com.xpf.dubbo.dubbotool.vo.GroupVO;
import com.xpf.dubbo.dubbotool.vo.InterfaceVO;
import com.xpf.dubbo.dubbotool.vo.RegistryVO;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
@RestController
@RequestMapping("/dubbo")
@SelLog
public class DubboController {

    @Autowired
    private IDubboService dubboService;

    @RequestMapping("/listGroup")
    public ResultDTO<Object> listGroup(@RequestBody RegistryVO vo) {
        List<String> list;
        try {
            list = dubboService.listGroup(vo);
        }
        catch (Exception e) {
            return ResultDTO.createError(e.getMessage(), e);
        }
        return ResultDTO.createSuccess("", list);
    }

    @RequestMapping("/listInterface")
    public ResultDTO<Object> listInterface(@RequestBody GroupVO vo) {
        List<String> list;
        try {
            list = dubboService.listInterface(vo);
        }
        catch (Exception e) {
            return ResultDTO.createError(e.getMessage(), e);
        }
        return ResultDTO.createSuccess("", list);
    }

    @RequestMapping("/listMethod")
    public ResultDTO<Object> listMethod(@RequestBody InterfaceVO vo) {
        List<String> list;
        try {
            list = dubboService.listMethod(vo);
        }
        catch (Exception e) {
            return ResultDTO.createError(e.getMessage(), e);
        }
        return ResultDTO.createSuccess("", list);
    }

    @RequestMapping("/doAchieve")
    public ResultDTO<Object> doAchieve(@RequestBody AchieveVO vo) throws NoSuchFieldException, IllegalAccessException {
        try {
            ResultDTO<Object> result = dubboService.doAchieve(vo);
            return result;
        } catch (Exception e) {
            return ResultDTO.createError(e.getMessage(), e);
        }
    }

}