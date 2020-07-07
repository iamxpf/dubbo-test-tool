package com.xpf.dubbo.dubbotool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xpf.dubbo.dubbotool.annotation.SelLog;
import com.xpf.dubbo.dubbotool.dto.ResultDTO;
import com.xpf.dubbo.dubbotool.service.IZookeeperService;
import com.xpf.dubbo.dubbotool.vo.RegistryVO;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
@RestController
@RequestMapping("/zk")
@SelLog
public class ZookeeperController {

    @Autowired
    private IZookeeperService zookeeperService;

    @RequestMapping("/listRegistry")
    public ResultDTO<Object> listRegistry() {
        List<RegistryVO> vos = zookeeperService.listRegistry();
        return ResultDTO.createSuccess("", vos);
    }

    @RequestMapping("/addRegistry")
    public ResultDTO<RegistryVO> addRegistry(@RequestBody RegistryVO vo) {
        ResultDTO<RegistryVO> result = zookeeperService.addRegistry(vo);
        return result;
    }

    @RequestMapping("/delRegistry")
    public ResultDTO<RegistryVO> delRegistry(@RequestBody RegistryVO vo) {
        ResultDTO<RegistryVO> result = zookeeperService.delRegistry(vo);
        return result;
    }
}
