package com.hxx.yi.controller;


import com.hxx.yi.service.StorekeeperService;

import com.yi.common.entity.Storekeeper;
import com.yi.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storekeeper")
public class StorekeeperController {

    @Autowired
    private StorekeeperService storekeeperService;

    @PostMapping("/register")
    public ResponseResult<Object> register(@RequestBody Storekeeper storekeeper) {
        return storekeeperService.register(storekeeper);
    }

    @PostMapping("/login")
    public ResponseResult<Object> login(@RequestBody Storekeeper storekeeper){
        return storekeeperService.login(storekeeper);
    }
}
