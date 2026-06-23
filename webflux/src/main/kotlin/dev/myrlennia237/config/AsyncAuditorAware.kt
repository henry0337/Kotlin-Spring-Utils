package dev.myrlennia237.config

import dev.myrlennia237.security.UserPrincipal
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import reactor.core.publisher.Mono
import java.util.UUID

/**
 * Implementation mặc định của [ReactiveAuditorAware] trả về [UUID] thực của người dùng hiện tại.
 *
 * Lấy UUID từ principal thông qua interface [UserPrincipal]. Để tích hợp, implement
 * [UserPrincipal] trên `UserDetails` của ứng dụng và expose [UserPrincipal.userId].
 *
 * Trả về [Mono.empty] nếu chưa xác thực hoặc principal không implement [UserPrincipal].
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
class AsyncAuditorAware : ReactiveAuditorAware<UUID> {
    override fun getCurrentAuditor(): Mono<UUID> =
        ReactiveSecurityContextHolder.getContext().flatMap { ctx ->
            val auth = ctx.authentication ?: return@flatMap Mono.empty()
            if (!auth.isAuthenticated) return@flatMap Mono.empty()
            (auth.principal as? UserPrincipal)
                ?.let { Mono.just(it.userId) }
                ?: Mono.empty()
        }
}
