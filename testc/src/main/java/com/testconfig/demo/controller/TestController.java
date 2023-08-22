package com.testconfig.demo.controller;

import com.freeman.properties.FreeDict;
import com.freeman.service.FreeDictService;
import com.freeman.tools.DataUtils;
import com.testconfig.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;

/**
 * @author wangchao
 * @Description:
 * @date 2023/8/18 11:08
 * @vVersion 1.0
 */
@RestController
public class TestController {
    @Autowired
    FreeDict freeDict;
    @Autowired
    FreeDictService freeDictService;
    @GetMapping("/h")
    public String hello() throws Exception {
        User u=new User();
        Boolean b=DataUtils.useList(new String[]{"1,2,3,4","2"},"2");
        return b+"结果是："+freeDict.getDictTableName();
    }

    @GetMapping("/h2")
    public String hello2() throws Exception {
        User u=new User();
        return "结果是："+freeDictService.queryOneDict(u)+",u:"+u.toString();
    }
}
