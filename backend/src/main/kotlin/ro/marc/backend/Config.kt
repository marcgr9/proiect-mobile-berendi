package ro.marc.backend

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import ro.marc.backend.security.authentication.AuthenticationFilter
import ro.marc.backend.security.authentication.AuthenticationSuccessHandler
import ro.marc.backend.security.jwt.JwtAuthorizationFilter
import ro.marc.backend.security.jwt.JwtAuthorizationUtils

@Configuration
class Config(
    private val authenticationSuccessHandler: AuthenticationSuccessHandler,
) {

    class PassEncoder: PasswordEncoder {
        override fun encode(rawPassword: CharSequence?): String = rawPassword.toString()

        override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean
            = rawPassword.toString() == encodedPassword.toString()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PassEncoder()

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager
            = authenticationConfiguration.authenticationManager

    @Bean
    fun filterChain(http: HttpSecurity, authenticationManager: AuthenticationManager, jwtAuthorizationUtils: JwtAuthorizationUtils): SecurityFilterChain? {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeHttpRequests { auth ->
                try {
                    auth
                        .anyRequest().permitAll()
                        .and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .addFilter(authenticationFilter(authenticationManager))
                        .addFilter(authorizationFilter(authenticationManager, jwtAuthorizationUtils))
                        .exceptionHandling()
                        .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationFilter(authenticationManager: AuthenticationManager) =
        AuthenticationFilter(ObjectMapper()).apply {
                setAuthenticationManager(authenticationManager)
                setAuthenticationSuccessHandler(authenticationSuccessHandler)
            }

    @Bean
    fun authorizationFilter(authenticationManager: AuthenticationManager, jwtAuthorizationUtils: JwtAuthorizationUtils): JwtAuthorizationFilter
            = JwtAuthorizationFilter(authenticationManager, jwtAuthorizationUtils, ObjectMapper())


}
