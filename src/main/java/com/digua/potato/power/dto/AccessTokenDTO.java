package com.digua.potato.power.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"client_secret","redirect_uri"})
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
