package com.example.hellospringbootkt.repositories

import com.example.hellospringbootkt.entities.CarSimple
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CarSimpleRepository : JpaRepository<CarSimple, Int> {
}
