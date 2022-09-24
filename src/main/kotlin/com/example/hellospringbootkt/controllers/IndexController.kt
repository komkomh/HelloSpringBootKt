package com.example.hellospringbootkt.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HealthcheckController() {

    @GetMapping("")
    fun read(): String {
        return "hello!";
    }
}

