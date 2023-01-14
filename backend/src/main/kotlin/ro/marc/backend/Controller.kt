package ro.marc.backend

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ro.marc.backend.dto.EntityDTO
import ro.marc.backend.entity.Entity
import ro.marc.backend.messaging.BusinessMessage
import ro.marc.backend.messaging.BusinessPayload
import ro.marc.backend.security.User

@RestController
class Controller(
    private val repository: Repository
) {

    @GetMapping("/")
    fun getAll(@AuthenticationPrincipal user: User)
        = BusinessPayload(
            repository.findAllWithOwner(user.id).map {
                EntityDTO.from(it)
            }
        )

    @PostMapping("/")
    fun add(@AuthenticationPrincipal user: User, @RequestBody data: EntityDTO)
        = BusinessPayload(
            EntityDTO.from(repository.save(Entity.from(data, user)))
        )

    @PatchMapping("/{id}")
    fun patch(@AuthenticationPrincipal user: User, @PathVariable("id") entityId: String, data: EntityDTO): BusinessPayload<Void> {
        repository.update(entityId.toLong(), data.name, data.quantity, data.date, data.isFavourite)
        return BusinessPayload(BusinessMessage.OK)
    }

}
