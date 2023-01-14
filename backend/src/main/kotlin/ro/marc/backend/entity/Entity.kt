package ro.marc.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import ro.marc.backend.dto.EntityDTO
import ro.marc.backend.security.User
import java.util.Date

@Entity
@Table(name = "entities")
data class Entity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    val owner: User = User(),

    val name: String = "",

    val quantity: Int = 0,

    val date: Date? = null,

    @Column(name = "is_favourite")
    val isFavourite: Boolean = false,

) {

    companion object {
        fun from(dto: EntityDTO, user: User) = Entity(
            owner = user,
            name = dto.name,
            quantity = dto.quantity,
            date = dto.date,
            isFavourite = dto.isFavourite,
        )
    }

}