package dev.myrlennia237.config

import dev.myrlennia237.contract.UserPrincipal
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional
import java.util.UUID

/**
 * Phiên bản triển khai của interface [AuditorAware], sử dụng [UUID] làm đối tượng tham chiếu khi sử dụng chức năng Audit.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @sample dev.myrlennia237.sample.FooComponent3
 */
public class BlockingAuditorAware : AuditorAware<UUID> {
    override fun getCurrentAuditor(): Optional<UUID> {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated) return Optional.empty()
        val principal = auth.principal
        return if (principal is UserPrincipal) Optional.of(principal.userId) else Optional.empty()
    }
}
