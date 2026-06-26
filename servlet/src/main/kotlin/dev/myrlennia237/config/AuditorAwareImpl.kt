package dev.myrlennia237.config

import dev.myrlennia237.component.UserPrincipal
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional
import java.util.UUID

/**
 * Implementation mặc định của [AuditorAware] trả về [UUID] thực của người dùng hiện tại.
 *
 * Lấy UUID từ principal thông qua interface [UserPrincipal]. Để tích hợp, implement
 * [UserPrincipal] trên `UserDetails` của ứng dụng và expose [UserPrincipal.userId].
 *
 * Trả về [Optional.empty] nếu chưa xác thực hoặc principal không implement [UserPrincipal].
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public class AuditorAwareImpl : AuditorAware<UUID> {
    override fun getCurrentAuditor(): Optional<UUID> {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated) return Optional.empty()
        val principal = auth.principal
        return if (principal is UserPrincipal) Optional.of(principal.userId) else Optional.empty()
    }
}
