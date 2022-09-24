package com.example.hellospringbootkt.entities

import java.io.Serializable
import javax.persistence.*

// 部品を表現する
@Entity
@Table(name = "parts")
data class Part(
    // 部品ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    @ManyToOne
    @JoinColumn(name = "car_id")
    val car: Car,
    // 部品タイプ
    @Enumerated(EnumType.STRING)
    var partType: PartType,
    // 名前
    @Column(length = 100, nullable = false)
    val name: String,
    // 価格
    @Column(nullable = false)
    val price: Long
) : Serializable

