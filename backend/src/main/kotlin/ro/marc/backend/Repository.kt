package ro.marc.backend

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ro.marc.backend.entity.Entity
import java.util.Date

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

    @Transactional
    @Modifying
    @Query(
        nativeQuery = true,
        value =
        """
            UPDATE entities SET name = :name, quantity = :quantity, date = :date, is_favourite = :isFavourite WHERE id = :id 
        """
    )
    fun update(id: Long, name: String, quantity: Int, date: Date, isFavourite: Boolean)

}