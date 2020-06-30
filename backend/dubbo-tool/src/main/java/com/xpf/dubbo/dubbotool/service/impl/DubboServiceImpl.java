package com.xpf.dubbo.dubbotool.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xpf.dubbo.dubbotool.cache.ConnectHandlerCache;
import com.xpf.dubbo.dubbotool.dto.ZookeeperDTO;
import com.xpf.dubbo.dubbotool.handler.ConnectHandler;
import com.xpf.dubbo.dubbotool.service.IDubboService;
import com.xpf.dubbo.dubbotool.util.ParamUtil;
import com.xpf.dubbo.dubbotool.vo.GroupVO;
import com.xpf.dubbo.dubbotool.vo.InterfaceVo;
import com.xpf.dubbo.dubbotool.vo.RegistryVO;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
@Service
public class DubboServiceImpl implements IDubboService {

    @Override
    public List<String> listGroup(RegistryVO vo) throws NoSuchFieldException, IllegalAccessException {
        ZookeeperDTO dto = ParamUtil.dealConVo(vo);
        ConnectHandler connectHandler = ConnectHandlerCache.getConnectHandler(dto);
        return connectHandler.getGroup();
    }

    @Override
    public List<String> listInterface(GroupVO vo) throws NoSuchFieldException, IllegalAccessException {
        ZookeeperDTO dto = ParamUtil.dealConVo(vo);
        dto.setGroup(vo.getGroup());
        ConnectHandler connectHandler = ConnectHandlerCache.getConnectHandler(dto);
        return connectHandler.getInterfaces(dto.getGroup());
    }

    @Override
    public List<String> listMethod(InterfaceVo vo) throws NoSuchFieldException, IllegalAccessException {
        ZookeeperDTO dto = ParamUtil.dealConVo(vo);
        dto.setGroup(vo.getGroup());
        ConnectHandler connectHandler = ConnectHandlerCache.getConnectHandler(dto);
        return connectHandler.getMethod(vo.getServiceUrl());
    }
}
