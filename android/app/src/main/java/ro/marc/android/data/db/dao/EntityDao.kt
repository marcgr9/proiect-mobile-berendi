package ro.marc.android.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ro.marc.android.data.db.entity.DbEntity
import java.util.*

@Dao
interface EntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(payment: DbEntity): Long

    @Query("SELECT * FROM entities ORDER BY local_id DESC")
    fun fetch(): List<DbEntity>

    @Query("DELETE FROM entities")
    fun removeAll()

    @Query("DELETE FROM entities WHERE local_id = :id")
    fun removeByLocalId(id: Long)

    @Query("UPDATE entities SET name = :name, quantity = :quantity, date = :date, is_favourite = :fav WHERE local_id = :id")
    fun updateByLocalId(id: Long, name: String, quantity: Int, date: Date, fav: Boolean)

    @Query("SELECT * FROM entities WHERE local_id = :id")
    fun getByLocalId(id: Long): DbEntity

}
