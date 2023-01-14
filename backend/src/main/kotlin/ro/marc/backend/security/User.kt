package ro.marc.backend.security

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name = "users")
data class User(
    @Column
    private var username: String = "",

    @Column
    private var password: String = "",
): UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}