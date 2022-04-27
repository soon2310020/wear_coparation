package com.example.wear_shop.controller;

import com.example.wear_shop.data.Entity.New;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/new")
public class NewController {
    @PostMapping("/")
    public void addNews(@RequestBody New news)
    {

    }
}
