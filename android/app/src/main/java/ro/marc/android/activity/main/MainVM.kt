package ro.marc.android.activity.main

import androidx.lifecycle.ViewModel
import ro.marc.android.data.api.dto.EntityDTO
import ro.marc.android.data.db.entity.LocalEntityStatus
import ro.marc.android.data.model.Entity
import ro.marc.android.data.repo.EntitiesRepo
import ro.marc.android.data.repo.LocalRepo

class MainVM(
    private val localRepo: LocalRepo,
    private val entitiesRepo: EntitiesRepo,
): ViewModel() {

    var entity: Entity? = null

    fun getLocalEntities() = localRepo.getAll()

    fun saveLocalEntity(entity: Entity, status: LocalEntityStatus = LocalEntityStatus.NEW): Entity
        = localRepo.save(entity, status)

    fun clearLocalEntities() {
        localRepo.clear()
    }

    fun update(localId: Long, entity: Entity, status: LocalEntityStatus): Entity
        = localRepo.updateByLocalId(localId, entity, status)

    fun setIdToLocalEntity(id: Long, localId: Long) {
        localRepo.setId(id, localId)
    }

    fun setCommitted(localId: Long) = localRepo.setCommitted(localId)

    fun getUncommitted() = localRepo.getUncommitted()

    fun getUpdated() = localRepo.getUpdated()

    fun getEntities() = entitiesRepo.getAll()

    fun postEntity(entityDTO: EntityDTO) = entitiesRepo.add(entityDTO)

    fun patch(entityDTO: EntityDTO, id: Long) = entitiesRepo.update(entityDTO, id)

}