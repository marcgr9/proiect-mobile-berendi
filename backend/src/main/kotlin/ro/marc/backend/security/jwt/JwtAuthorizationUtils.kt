package ro.marc.backend.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import ro.marc.backend.security.UserService


@Service
class JwtAuthorizationUtils(

    @Autowired
    private val userService: UserService,

) {

    fun getAuthentication(request: HttpServletRequest) = getAuthFromToken(request.getHeader(HttpHeaders.AUTHORIZATION))

    fun getAuthentication(token: String) = getAuthFromToken(token)

    private fun getAuthFromToken(token: String?): UsernamePasswordAuthenticationToken? {
        if (token == null || !token.startsWith("Berendi ")) {
            return null
        }

        try {
            val username: String = JWT.require(Algorithm.HMAC256("berendi"))
                .build()
                .verify(token.replace("Berendi ", ""))
                .subject ?: return null

            val userDetails = userService.loadUserByUsername(username)

            return UsernamePasswordAuthenticationToken(userDetails as UserDetails, null, userDetails.authorities)
        } catch (ex: Exception) {
            return null
        }
    }
}