package com.victor2022.httpclient.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author: victor2022
 * @date: 2022/4/22 下午9:40
 * @description:
 */

@RestController
@RequestMapping(value = "test", produces = {"application/json;charset=UTF-8"})
@ResponseBody
public class TestController {

    @GetMapping("testGet")
    public String test(){
        return "{\"msg\":\"处理返回\"}";
    }

    @GetMapping("testGet/{param}")
    public String testWithParam(@PathVariable String param){
        return "{\n" +
                "    \"msg\":\"有参请求的返回\",\n" +
                "    \"param\":"+
                param +
                "\n}";
    }
}
