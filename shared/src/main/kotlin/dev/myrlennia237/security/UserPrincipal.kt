package dev.myrlennia237.security

import java.util.UUID

/**
 * Contract cho principal của người dùng đã xác thực, yêu cầu expose UUID thực từ cơ sở dữ liệu.
 *
 * Implement interface này trên `UserDetails` (hoặc principal tương ứng) của ứng dụng để
 * tích hợp với cơ chế auditing ([org.springframework.data.domain.AuditorAware]) của thư viện.
 *
 * Ví dụ:
 * ```kotlin
 * class AppUserDetails(
 *     override val userId: UUID,
 *     private val username: String,
 *     private val password: String,
 *     authorities: Collection<GrantedAuthority>
 * ) : User(username, password, authorities), UserPrincipal
 * ```
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
interface UserPrincipal {
    /**
     * UUID thực của người dùng trong cơ sở dữ liệu.
     */
    val userId: UUID
}
