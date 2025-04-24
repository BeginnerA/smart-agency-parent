package com.reid.smart.agency.support.openapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tt.smart.agency.component.securityprotect.openapi.annotation.OpenApiAuth;
import tt.smart.agency.component.securityprotect.openapi.annotation.OpenApiUserAuth;

/**
 * <p>
 * 开放接口认证测试
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api")
public class OpenApiAuthTestController {

    @OpenApiUserAuth
    @PostMapping("/testUserAuth/{appId}")
    public String testUserAuth(@PathVariable("appId") Long appId) {
        return "通过普通认证";
    }

    @OpenApiAuth
    @PostMapping("/testAuth/{appId}")
    public String testAuth(@PathVariable("appId") Long appId) {
        return "通过增强认证";
    }

}
