package dev.myrlennia237.config

import dev.myrlennia237.contract.UserPrincipal
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import reactor.core.publisher.Mono
import java.util.UUID

/**
 * Phiên bản [AuditorAware] dành cho môi trường reactive sử dụng [UUID] làm đối tượng tham chiếu tới auditor.
 * 
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
public class AsyncAuditorAware : ReactiveAuditorAware<UUID> {
    override fun getCurrentAuditor(): Mono<UUID> =
        ReactiveSecurityContextHolder.getContext().flatMap { ctx ->
            val auth = ctx.authentication ?: return@flatMap Mono.empty()
            if (!auth.isAuthenticated) return@flatMap Mono.empty()
            (auth.principal as? UserPrincipal)?.let { Mono.just(it.userId) }
                ?: Mono.empty()
        }
}
