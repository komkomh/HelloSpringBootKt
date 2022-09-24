package com.example.hellospringbootkt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloSpringBootKtApplication

fun main(args: Array<String>) {
    runApplication<HelloSpringBootKtApplication>(*args)
}
