package ro.marc.android.activity.main

import androidx.lifecycle.ViewModel
import ro.marc.android.data.db.entity.LocalEntityStatus
import ro.marc.android.data.model.Entity
import ro.marc.android.data.repo.LocalRepo

class MainVM(
    private val localRepo: LocalRepo,
): ViewModel() {

    var entity: Entity? = null

    fun getLocalEntities() = localRepo.getAll()

    fun saveLocalEntity(entity: Entity): Entity
        = localRepo.save(entity, LocalEntityStatus.NEW)

    fun update(localId: Long, entity: Entity): Entity
        = localRepo.updateByLocalId(localId, entity)

}