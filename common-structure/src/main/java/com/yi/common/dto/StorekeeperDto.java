package com.yi.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StorekeeperDto implements Serializable {

    private String username;

    private String password;

    private String confirm;
}
