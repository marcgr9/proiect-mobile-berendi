package ro.marc.backend

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ro.marc.backend.messaging.BusinessPayload
import ro.marc.backend.security.User

@RestController
class Controller(
    private val repository: Repository
) {

    @GetMapping("/")
    fun getAll(@AuthenticationPrincipal user: User)
        = BusinessPayload(
            repository.findAllWithOwner(user.id)
        )

}
