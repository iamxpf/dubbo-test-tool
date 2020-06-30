package com.xpf.dubbo.dubbotool.service;

import java.util.List;

import com.xpf.dubbo.dubbotool.dto.ResultDTO;
import com.xpf.dubbo.dubbotool.vo.RegistryVO;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
public interface IZookeeperService {

    ResultDTO<RegistryVO> addRegistry(RegistryVO vo);

    ResultDTO<RegistryVO> delRegistry(RegistryVO vo);

    List<RegistryVO> listRegistry();

}
