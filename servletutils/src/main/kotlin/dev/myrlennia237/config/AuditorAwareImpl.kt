@file:Suppress("LossyEncoding")

package dev.myrlennia237.config

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
class AuditorAwareImpl : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        val auth: Authentication? = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated) return Optional.of("system")
        return Optional.of(auth.name.ifBlank { "system" })
    }
}
