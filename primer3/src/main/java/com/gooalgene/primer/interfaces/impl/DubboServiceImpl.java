package com.gooalgene.primer.interfaces.impl;

import com.gooalgene.primer.interfaces.DubboService;
import org.springframework.stereotype.Service;

@Service("dubboService")
public class DubboServiceImpl implements DubboService {
    public String say() {
        return "hello world";
    }
}
