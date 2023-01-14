package ro.marc.backend.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import ro.marc.backend.messaging.BusinessMessage
import ro.marc.backend.messaging.BusinessPayload


class JwtAuthorizationFilter(

    authManager: AuthenticationManager,

    private val jwtAuthorizationUtils: JwtAuthorizationUtils,

    private val objectMapper: ObjectMapper,

) : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val auth = jwtAuthorizationUtils.getAuthentication(request)
        if (auth == null) {
            response.status = 401
            response.writer.write(
                objectMapper.writeValueAsString(BusinessPayload<Void>(BusinessMessage.UNAUTHORIZED))
            )
            return
        }
        SecurityContextHolder.getContext().authentication = auth
        chain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val pathParts = request.requestURI.split("/").filter { it.isNotBlank() }
        return pathParts[0] == "login"
    }

}
