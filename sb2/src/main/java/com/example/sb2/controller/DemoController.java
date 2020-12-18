package com.example.sb2.controller;

import com.example.sb2.bean.Demo;
import com.example.sb2.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/hello/{id}")
    @ResponseBody
    public String hello(@PathVariable(value="id") int id) {
        return Optional.ofNullable(demoService.getDemoById(id)).map(Demo::toString)
                .orElse("empty result");
     }

}
