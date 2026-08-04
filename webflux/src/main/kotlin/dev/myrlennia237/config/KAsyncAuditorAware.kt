package dev.myrlennia237.config

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.contract.UserPrincipal
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import reactor.core.publisher.Mono
import kotlin.uuid.Uuid
import kotlin.uuid.toKotlinUuid

/**
 * Phiên bản [AuditorAware] dành cho môi trường reactive sử dụng [Uuid] làm đối tượng tham chiếu tới auditor.
 *
 * **Ghi chú**: Chỉ có các **"K" variant** trong thư viện này và các Kotlin consumer mới nên sử dụng lớp này.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@KotlinVariant
public class KAsyncAuditorAware : ReactiveAuditorAware<Uuid> {
    override fun getCurrentAuditor(): Mono<Uuid> =
        ReactiveSecurityContextHolder.getContext().flatMap { ctx ->
            val auth = ctx.authentication ?: return@flatMap Mono.empty()
            if (!auth.isAuthenticated) return@flatMap Mono.empty()
            (auth.principal as? UserPrincipal)?.let {
                Mono.just(it.userId.toKotlinUuid())
            } ?: Mono.empty()
        }
}
