package com.example.hellospringbootkt.entities

import java.math.BigDecimal
import java.math.RoundingMode

// 車種を表現する
enum class BodyType(val view: String, val taxRate: BigDecimal) {
    Kei("軽自動車", BigDecimal("0.9")),
    Sedan("セダン", BigDecimal("1.0")),
    Suv("SUV", BigDecimal("1.1")),
    Minivan("ミニバン", BigDecimal("1.2")),
    Wagon("ワゴン", BigDecimal("1.3"));

    // 税込み価格を取得する
    fun getTaxPrice(price: BigDecimal): BigDecimal {
        val taxPrice = price * taxRate
        return taxPrice.setScale(2, RoundingMode.DOWN)
    }
}

// 部品の種類を表現する
enum class PartType(val view: String) {
    Engine("エンジン"),
    Body("車体"),
    Tire("タイヤ"),
    Handle("ハンドル")
}