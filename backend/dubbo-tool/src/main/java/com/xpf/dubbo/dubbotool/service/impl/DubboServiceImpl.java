package com.xpf.dubbo.dubbotool.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.Constants;
import com.xpf.dubbo.dubbotool.cache.ConnectHandlerCache;
import com.xpf.dubbo.dubbotool.constant.Const;
import com.xpf.dubbo.dubbotool.dto.ResultDTO;
import com.xpf.dubbo.dubbotool.dto.ZookeeperDTO;
import com.xpf.dubbo.dubbotool.handler.ConnectHandler;
import com.xpf.dubbo.dubbotool.service.IDubboService;
import com.xpf.dubbo.dubbotool.util.ParamUtil;
import com.xpf.dubbo.dubbotool.vo.AchieveVO;
import com.xpf.dubbo.dubbotool.vo.GroupVO;
import com.xpf.dubbo.dubbotool.vo.InterfaceVO;
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
    public List<String> listMethod(InterfaceVO vo) throws NoSuchFieldException, IllegalAccessException {
        ZookeeperDTO dto = ParamUtil.dealConVo(vo);
        dto.setGroup(vo.getGroup());
        ConnectHandler connectHandler = ConnectHandlerCache.getConnectHandler(dto);
        return connectHandler.getMethod(vo.getServiceUrl());
    }

    @Override
    public ResultDTO<Object> doAchieve(AchieveVO vo){
        ApplicationConfig application = new ApplicationConfig();
        application.setName(Const.APPLICATION_NAME);

        RegistryConfig registry = new RegistryConfig();
        ZookeeperDTO dto = ParamUtil.dealConVo(vo);
        Map<String, String> groupParam = new HashMap<>();
        groupParam.put(Constants.GROUP_KEY, vo.getGroup());
        URL url = new URL(Const.REGISTRY_PROTOCOL, dto.getHost(), dto.getPort(), groupParam);
        registry.setAddress(url.toString());
        //application.setRegistry(registry);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setInterface(vo.getServiceUrl());
        reference.setGeneric(true);
        reference.setTimeout(5000);
        reference.setRegistry(registry);

        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
//        GenericService genericService = reference.get();
        ReferenceConfigCache cache = ReferenceConfigCache.getCache(Const.APPLICATION_NAME, CACHE_KEY_GENERATOR);
        GenericService genericService = cache.get(reference);

        // 如果返回POJO将自动转成Map
        String[] paramTypes = vo.getParams() == null? null : vo.getParams().stream().map(e -> e.getParamType()).toArray(String[]::new);
        Object[] paramsContext = vo.getParams() == null? null : vo.getParams().stream().map(e -> e.getParamContext()).toArray();
        Object result = genericService.$invoke(vo.getMethod(), paramTypes, paramsContext);
        return ResultDTO.createSuccess("", result);
    }
    public ReferenceConfigCache.KeyGenerator CACHE_KEY_GENERATOR = referenceConfig -> {
        String iName = referenceConfig.getInterface();
        if(StringUtils.isBlank(iName)) {
            Class<?> clazz = referenceConfig.getInterfaceClass();
            iName = clazz.getName();
        }
        if(StringUtils.isBlank(iName)) {
            throw new IllegalArgumentException("No interface info in ReferenceConfig" + referenceConfig);
        }

        StringBuilder ret = new StringBuilder();
        if(! StringUtils.isBlank(referenceConfig.getGroup())) {
            ret.append(referenceConfig.getGroup()).append("/");
        }
        ret.append(iName);
        if(! StringUtils.isBlank(referenceConfig.getVersion())) {
            ret.append(":").append(referenceConfig.getVersion());
        }
        String registry = referenceConfig.getRegistry().getAddress();
        ret.append(":").append(registry);
        return ret.toString();
    };
}
