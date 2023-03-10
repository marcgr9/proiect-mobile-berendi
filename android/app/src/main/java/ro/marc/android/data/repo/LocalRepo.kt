package ro.marc.android.data.repo

import ro.marc.android.data.db.dao.EntityDao
import ro.marc.android.data.db.entity.DbEntity
import ro.marc.android.data.db.entity.LocalEntityStatus
import ro.marc.android.data.model.Entity
import ro.marc.android.util.Utils

class LocalRepo(
    private val dao: EntityDao,
) {

    fun getAll(): List<Entity> = dao.fetch().map(Utils::asEntity)

    fun save(entity: Entity, status: LocalEntityStatus): Entity {
        val localId = dao.save(
            DbEntity(
                id = entity.id,
                name = entity.name,
                quantity = entity.quantity,
                date = entity.date,
                isFavourite = entity.isFavourite,
                status = status,
            )
        )

        return Utils.asEntity(dao.getByLocalId(localId))
    }

    fun updateByLocalId(localId: Long, entity: Entity, status: LocalEntityStatus): Entity {
        dao.updateByLocalId(
            localId,
            name = entity.name,
            quantity = entity.quantity,
            date = entity.date,
            fav = entity.isFavourite,
            status = status
        )

        return Utils.asEntity(dao.getByLocalId(localId))
    }

    fun clear() {
        dao.removeAll()
    }

    fun getUncommitted() = dao.getUncommitted()

    fun getUpdated() = dao.getUpdated()

    fun setCommitted(localId: Long) {
        dao.setCommitted(localId)
    }

    fun setId(id: Long, localId: Long) {
        dao.setId(id, localId)
    }

}
