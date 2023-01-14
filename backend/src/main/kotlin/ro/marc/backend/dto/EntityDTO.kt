package ro.marc.backend.dto

import ro.marc.backend.entity.Entity
import java.util.*

class EntityDTO(

    val id: Long?,

    val name: String,

    val quantity: Int,

    val date: Date,

    val isFavourite: Boolean,

) {

    companion object {
        fun from(entity: Entity) = EntityDTO(
            entity.id,
            entity.name,
            entity.quantity,
            entity.date!!,
            entity.isFavourite
        )
    }

}