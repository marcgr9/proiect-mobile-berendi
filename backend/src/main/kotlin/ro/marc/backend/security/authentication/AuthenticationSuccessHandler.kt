package ro.marc.backend.security.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import ro.marc.backend.messaging.BusinessPayload
import ro.marc.backend.security.User
import ro.marc.backend.security.UserService
import ro.marc.backend.security.jwt.TokenDTO


@Component
class AuthenticationSuccessHandler(

    private val userService: UserService,

) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val principal = authentication.principal as User
        println(principal)
        val token = JWT.create()
            .withSubject(userService.loadUserByUsername(principal.username).username)
            .sign(Algorithm.HMAC256("berendi"))

        response.setHeader(HttpHeaders.AUTHORIZATION, "Berendi $token")
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        response.writer.write(
            ObjectMapper().writeValueAsString(BusinessPayload(TokenDTO("Berendi $token")))
        )
    }

}