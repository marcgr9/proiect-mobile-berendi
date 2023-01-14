package ro.marc.android.data.api.dto

import java.util.Date


class EntityDTO(

    val id: Long?,

    val name: String,

    val quantity: Int,

    val date: Date,

    val isFavourite: Boolean,

)
