package com.example.hellospringbootkt.repositories

import com.example.hellospringbootkt.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
}
