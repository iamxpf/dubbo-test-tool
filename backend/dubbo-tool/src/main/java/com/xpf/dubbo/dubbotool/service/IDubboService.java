package com.xpf.dubbo.dubbotool.service;

import java.util.List;

import com.xpf.dubbo.dubbotool.dto.ResultDTO;
import com.xpf.dubbo.dubbotool.vo.AchieveVO;
import com.xpf.dubbo.dubbotool.vo.GroupVO;
import com.xpf.dubbo.dubbotool.vo.InterfaceVO;
import com.xpf.dubbo.dubbotool.vo.RegistryVO;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
public interface IDubboService {
    List<String> listGroup(RegistryVO conUrl) throws NoSuchFieldException, IllegalAccessException;

    List<String> listInterface(GroupVO vo) throws NoSuchFieldException, IllegalAccessException;

    List<String> listMethod(InterfaceVO vo) throws NoSuchFieldException, IllegalAccessException;

    ResultDTO<Object> doAchieve(AchieveVO vo) throws NoSuchFieldException, IllegalAccessException;
}
