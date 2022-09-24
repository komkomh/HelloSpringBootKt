package com.example.hellospringbootkt.entities

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.io.Serializable
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import javax.persistence.*

// 車を表現する
@Entity
@Table(name = "cars")
data class Car(
    // 車ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    // 車の名前
    @Column(length = 100, nullable = false)
    val name: String,
    // 車種
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    val bodyType: BodyType,
    // 価格
    @Column(nullable = false)
    val price: BigDecimal,
    // 作成者ID
    @Column(nullable = false)
    val createUserId: Int,
    // 作成日時
    @Column(nullable = false)
    val createdDateTime: LocalDateTime,
    // 更新者ID
    @Column(nullable = false)
    val updateUserId: Int,
    // 更新日時
    @Column(nullable = false)
    val updatedDateTime: LocalDateTime,
    @ManyToOne(optional = false)
    @JoinColumn(name = "createUserId", referencedColumnName = "id", insertable = false, updatable = false)
    val createUser: User,
    @OneToMany(mappedBy = "car", cascade = [CascadeType.ALL], orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    var parts: MutableList<Part>
) : Serializable
