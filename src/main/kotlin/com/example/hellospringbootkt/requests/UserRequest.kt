package com.example.hellospringbootkt.requests

import com.example.hellospringbootkt.entities.User

// ユーザ作成リクエスト
data class UserPostRequest(val name: String) {
    fun toEntity(): User {
        return User(null, name)
    }
}

// 車更新リクエスト
data class UserPutRequest(val name: String) {
    fun toEntity(user: User): User {
        return User(user.id, name)
    }
}
