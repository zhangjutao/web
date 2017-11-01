package com.gooalgene.entity;
import lombok.Data;

import java.util.Map;

/*
* ali短信对象，需要传入：username，phone,
* */
@Data
public class AliMessage {
    private User user;
    private String username;
    private String managerPhone;

    private Map templateParam;

    private String signName;

    private String templateCode;


}
