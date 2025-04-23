package com.reid.smart.agency.im.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试即时通讯
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/im")
public class ImController {

    @GetMapping("/test")
    public String test() {
        return "测试";
    }

}
