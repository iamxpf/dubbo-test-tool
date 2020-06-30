package com.xpf.dubbo.dubbotool.util;

import com.xpf.dubbo.dubbotool.dto.ZookeeperDTO;
import com.xpf.dubbo.dubbotool.vo.RegistryVO;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
public class ParamUtil {

    private ParamUtil(){}

    public static ZookeeperDTO dealConVo(RegistryVO vo) {
        String[] url = vo.getUrl().replace("：", ":").split(":");
        String host = url[0];
        String port = url[1];
        return new ZookeeperDTO(host, Integer.parseInt(port));
    }

}
