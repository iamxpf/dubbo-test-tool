package com.xpf.dubbo.dubbotool.vo;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author: xia.pengfei
 * @date: 2020/7/2
 */
@Data
public class AchieveVO extends MethodVO{
    private List<ParamVO> params;
}
