package ro.marc.backend.security.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ro.marc.backend.messaging.BusinessMessage
import ro.marc.backend.messaging.BusinessPayload
import kotlin.streams.asSequence

class AuthenticationFilter(

    private val objectMapper: ObjectMapper,

): UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        try {
            val credentials = objectMapper.readValue(
                request.reader.lines().asSequence().reduce { t, u -> t + u },
                LoginCredentials::class.java
            )
            val token = UsernamePasswordAuthenticationToken(credentials.username, credentials.password)

            setDetails(request, token)
            return authenticationManager.authenticate(token)
        } catch (badCredentialsException: BadCredentialsException) {
            response.writer.write(
                objectMapper.writeValueAsString(
                    BusinessPayload<Void>(BusinessMessage.BAD_CREDENTIALS)
                )
            )

            return null
        }

    }

}
