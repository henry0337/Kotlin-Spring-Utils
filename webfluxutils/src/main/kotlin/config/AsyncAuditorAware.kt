package config

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono

class AsyncAuditorAware : ReactiveAuditorAware<String> {
    override fun getCurrentAuditor(): Mono<String> = mono {
        val auth: Authentication? = ReactiveSecurityContextHolder.getContext().awaitSingle().authentication
        if ((auth == null) || !auth.isAuthenticated) {
            return@mono "system"
        }

        val principal: Any? = auth.principal
        if (principal is UserDetails) principal.username else ""
    }
}