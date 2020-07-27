package com.drekerd.testCard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {

    private final String IS_OK = "The application is OK and Running" + LocalDateTime.now();

    @GetMapping("/test")
    public String getHello(){
        return IS_OK;
    }

}
