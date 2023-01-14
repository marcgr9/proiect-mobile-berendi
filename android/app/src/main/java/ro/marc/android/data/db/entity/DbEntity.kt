package ro.marc.android.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "entities")
data class DbEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "local_id")
    val localId: Long = 0,

    val id: Long?,

    val name: String,

    val quantity: Int,

    val date: Date,

    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean,

    var status: LocalEntityStatus,

)
