package ro.marc.android.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ro.marc.android.data.db.entity.DbEntity
import ro.marc.android.data.db.entity.LocalEntityStatus
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

    @Query("UPDATE entities SET name = :name, quantity = :quantity, date = :date, is_favourite = :fav, status = :status WHERE local_id = :id")
    fun updateByLocalId(id: Long, name: String, quantity: Int, date: Date, fav: Boolean, status: LocalEntityStatus)

    @Query("SELECT * FROM entities WHERE local_id = :id")
    fun getByLocalId(id: Long): DbEntity

    @Query("SELECT * FROM entities WHERE status = 'NEW'")
    fun getUncommitted(): List<DbEntity>

    @Query("SELECT * FROM entities WHERE status = 'UPDATED'")
    fun getUpdated(): List<DbEntity>

    @Query("UPDATE entities SET status = 'COMMITTED' WHERE local_id = :localId")
    fun setCommitted(localId: Long)

    @Query("UPDATE entities SET id = :id WHERE local_id = :localId")
    fun setId(id: Long, localId: Long)

}
