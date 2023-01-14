package ro.marc.android.data.db

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ro.marc.android.data.db.dao.EntityDao
import ro.marc.android.data.db.entity.DbEntity

@androidx.room.Database(entities = [DbEntity::class], version = 2)
@TypeConverters(TypeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun dao(): EntityDao

}
