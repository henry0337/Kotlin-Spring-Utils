package dev.myrlennia237.config

import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import reactor.core.publisher.Mono

/**
 * Cung cấp tên người dùng hiện tại cho cơ chế audit của Spring Data R2DBC.
 *
 * Đọc thông tin từ [ReactiveSecurityContextHolder] và trả về `auth.name` nếu đã xác thực —
 * hoạt động với mọi loại [Authentication] (UserDetails, JWT, OAuth2, v.v.).
 * Trả về `"system"` nếu chưa có session bảo mật hoặc tên xác thực rỗng.
 *
 * Bean này được tự động đăng ký bởi [SpringUtilsAutoConfiguration]. Để override,
 * khai báo bean [ReactiveAuditorAware] riêng trong application context.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
class AsyncAuditorAware : ReactiveAuditorAware<String> {
    override fun getCurrentAuditor(): Mono<String> = mono {
        val auth: Authentication? = ReactiveSecurityContextHolder.getContext()
            .awaitSingleOrNull()?.authentication

        if (auth == null || !auth.isAuthenticated) return@mono "system"

        // auth.name hoạt động với mọi loại Authentication (UserDetails, JWT, OAuth2, v.v.)
        auth.name.ifBlank { "system" }
    }
}
