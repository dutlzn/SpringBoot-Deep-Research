package com.example.sb2.controller;

import com.example.sb2.bean.Demo;
import com.example.sb2.service.DemoService;
import com.example.sb2.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/demo")
public class DemoController {
    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    private DemoService demoService;
    @Autowired
    private TestService testService;

    @RequestMapping("/hello/{id}")
    @ResponseBody
    public String hello(@PathVariable(value = "id") int id) {
        LOG.info("hello");
        return Optional.ofNullable(demoService.getDemoById(id)).map(Demo::toString)
                .orElse("empty result");
    }


    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return testService.test();
    }

}
