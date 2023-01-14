package ro.marc.android.data.model

import java.util.*

data class Entity(
    val id: Long,
    val name: String,
    val quantity: Int,
    val date: Date,
    val isFavourite: Boolean,
)
