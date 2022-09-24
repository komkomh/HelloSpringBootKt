package com.example.hellospringbootkt.responses

import com.example.hellospringbootkt.entities.Car
import com.example.hellospringbootkt.entities.Part
import java.text.NumberFormat

// 車表示レスポンス
data class CarResponse(
    val id: Int,
    val name: String,
    val bodyName: String,
    val price: String,
    val taxRate: String,
    val taxPrice: String,
    val createdUserName: String,
    val parts: List<PartResponse>
) {
    companion object {
        fun create(car: Car): CarResponse {
            return CarResponse(
                car.id!!,
                car.name,
                car.bodyType.view,
                NumberFormat.getCurrencyInstance().format(car.price),
                car.bodyType.taxRate.toString(),
                NumberFormat.getCurrencyInstance().format(car.bodyType.getTaxPrice(car.price)),
                car.createUser.name,
                car.parts.map { PartResponse.create(it) }
            )
        }
    }
}

// 部品表示レスポンス
data class PartResponse(val partTypeName: String, val name: String, val price: String) {
    companion object {
        fun create(part: Part): PartResponse {
            return PartResponse(
                part.partType.view,
                part.name,
                NumberFormat.getCurrencyInstance().format(part.price)
            )
        }
    }
}
