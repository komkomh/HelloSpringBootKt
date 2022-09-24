package com.example.hellospringbootkt.controllers

import com.example.hellospringbootkt.entities.BodyType
import com.example.hellospringbootkt.entities.PartType
import com.example.hellospringbootkt.entities.User
import com.example.hellospringbootkt.exceptions.NotFoundException
import com.example.hellospringbootkt.repositories.CarRepository
import com.example.hellospringbootkt.requests.CarDetailSearchRequest
import com.example.hellospringbootkt.requests.CarPostRequest
import com.example.hellospringbootkt.requests.CarPutRequest
import com.example.hellospringbootkt.responses.CarResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("cars")
class CarRestController(private val carRepository: CarRepository) {

    @GetMapping("/simple_search/{bodyType}/{partType}")
    fun simpleSearch(@PathVariable bodyType: String, @PathVariable partType: String): List<CarResponse> {
        return carRepository.findByBodyTypeAndPartsPartType(BodyType.valueOf(bodyType), PartType.valueOf(partType))
            .map { car -> CarResponse.create(car) }
    }

    @PostMapping("/detail_search/")
    fun detailSearch(@RequestBody request: CarDetailSearchRequest): List<CarResponse> {
        return carRepository.detailSearch(request)
            .map { car -> CarResponse.create(car) }
    }

    @PostMapping("/")
    fun create(@RequestBody request: CarPostRequest): CarResponse {
        return carRepository.save(request.toEntity(getLoginUser()))
            .run { CarResponse.create(this) }
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Int): CarResponse {
        return carRepository.findById(id)
            .map { car -> CarResponse.create(car) }
            .orElseThrow { NotFoundException("ない") }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody request: CarPutRequest): CarResponse {
        return carRepository.findById(id)
            .map { car -> carRepository.save(request.toEntity(getLoginUser(), car)) }
            .map { car -> CarResponse.create(car) }
            .orElseThrow { NotFoundException("ない") }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        carRepository.deleteById(id)
    }

    // ログイン情報を取得するような処理
    fun getLoginUser(): User {
        return User(1, "おれおれ")
    }
}

