package dev.myrlennia237.config

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.contract.UserPrincipal
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import reactor.core.publisher.Mono
import kotlin.uuid.Uuid
import kotlin.uuid.toKotlinUuid

/**
 * Implementation mặc định của [ReactiveAuditorAware] trả về [Uuid] thực của người dùng hiện tại.
 *
 * Đây là bản Kotlin variant của [AsyncAuditorAware], dành cho các entity kế thừa từ
 * [dev.myrlennia237.template.entity.KEntity] — nơi các field auditing sử dụng [Uuid] thay vì [java.util.UUID].
 *
 * **Cách dùng**: Khai báo bean này trong `@Configuration` của ứng dụng để thay thế [AsyncAuditorAware] mặc định:
 * ```kotlin
 * @Bean
 * fun auditorAware() = KAsyncAuditorAware()
 * ```
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see AsyncAuditorAware
 */
@KotlinVariant
public class KAsyncAuditorAware : ReactiveAuditorAware<Uuid> {
    override fun getCurrentAuditor(): Mono<Uuid> =
        ReactiveSecurityContextHolder.getContext().flatMap { ctx ->
            val auth = ctx.authentication ?: return@flatMap Mono.empty()
            if (!auth.isAuthenticated) return@flatMap Mono.empty()
            (auth.principal as? UserPrincipal)?.let { Mono.just(it.userId.toKotlinUuid()) }
                ?: Mono.empty()
        }
}
