package com.example.hellospringbootkt.repositories

import com.example.hellospringbootkt.entities.*
import com.example.hellospringbootkt.requests.CarDetailSearchRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.criteria.*

@Repository
interface CarRepository : JpaRepository<Car, Int>, CarRepositoryCustom {
    // 簡易検索
    fun findByBodyTypeAndPartsPartType(bodyType: BodyType, partType: PartType): List<Car>
}

interface CarRepositoryCustom {
    // 詳細検索
    fun detailSearch(request: CarDetailSearchRequest): List<Car>
}

class CarRepositoryImpl(private val em: EntityManager) : CarRepositoryCustom {

    override fun detailSearch(request: CarDetailSearchRequest): List<Car> {
        val builder: CriteriaBuilder = em.criteriaBuilder
        val query: CriteriaQuery<Car> = builder.createQuery(Car::class.java)
        val root: Root<Car> = query.from(Car::class.java)
        val join: Join<Car, Part> = root.join(Car_.parts)

        root.fetch(Car_.createUser, JoinType.INNER)
        root.fetch(Car_.parts, JoinType.INNER)

        fun <T> builderEqual(path: Path<T>, value: T): Predicate = builder.equal(path, value)

        val params = mutableListOf<Predicate>()
        request.carName?.let { params.add(builder.like(root.get(Car_.name), "%${it}%")) }
        request.bodyTypes?.let { params.add(root.get(Car_.bodyType).`in`(request.bodyTypes)) }
        request.carPriceFrom?.let { params.add(builder.greaterThan(root.get(Car_.price), it)) }
        request.carPriceTo?.let { params.add(builder.lessThan(root.get(Car_.price), it)) }
        request.createUserId?.let { params.add(builderEqual(root.get(Car_.createUser).get(User_.id), it)) }
        request.createUserName?.let { params.add(builder.like(root.get(Car_.createUser).get(User_.name), "%${it}%")) }
        request.createdDateTimeFrom?.let { params.add(builder.greaterThan(root.get(Car_.createdDateTime), it)) }
        request.createdDateTimeTo?.let { params.add(builder.lessThan(root.get(Car_.createdDateTime), it)) }
        request.updateUserId?.let { params.add(builderEqual(root.get(Car_.updateUserId), it)) }
        request.updateDateTimeFrom?.let { params.add(builder.greaterThan(root.get(Car_.updatedDateTime), it)) }
        request.updateDateTimeTo?.let { params.add(builder.lessThan(root.get(Car_.updatedDateTime), it)) }
        request.partTypes?.let { params.add(join.get(Part_.partType).`in`(request.partTypes)) }
        request.partName?.let { params.add(builder.like(join.get(Part_.name), "%${it}%")) }
        request.partPriceFrom?.let { params.add(builder.greaterThan(join.get(Part_.price), it)) }
        request.partPriceTo?.let { params.add(builder.lessThan(join.get(Part_.price), it)) }

        query.select(root).distinct(true).where(*params.toTypedArray())
        return em.createQuery(query).resultList
    }
}