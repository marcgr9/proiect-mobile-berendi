package ro.marc.backend

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ro.marc.backend.entity.Entity

@Repository
interface Repository: JpaRepository<Entity, Long> {

    @Query(
        nativeQuery = true,
        value =
        """
            SELECT * FROM entities WHERE owner = :id
        """
    )
    fun findAllWithOwner(id: Long): List<Entity>

}