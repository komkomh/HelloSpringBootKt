package com.example.hellospringbootkt.controllers

import com.example.hellospringbootkt.entities.User
import com.example.hellospringbootkt.exceptions.NotFoundException
import com.example.hellospringbootkt.repositories.UserRepository
import com.example.hellospringbootkt.requests.UserPostRequest
import com.example.hellospringbootkt.requests.UserPutRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userRepository: UserRepository) {

    @PostMapping("/")
    fun create(@RequestBody request: UserPostRequest): User {
        return userRepository.save(request.toEntity())
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Int): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException("ない") }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody request: UserPutRequest): User {
        return userRepository.findById(id)
            .map { user -> userRepository.save(request.toEntity(user)) }
            .orElseThrow { NotFoundException("ない") }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        userRepository.deleteById(id)
    }

    @GetMapping("/")
    fun list(): List<User>{
        return userRepository.findAll();
    }
}

