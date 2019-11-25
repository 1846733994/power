package com.digua.potato.power.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String accountId;
    private String token;
    private String name;
    private Long gmtCreate;
    private Long gmtModified;
}
