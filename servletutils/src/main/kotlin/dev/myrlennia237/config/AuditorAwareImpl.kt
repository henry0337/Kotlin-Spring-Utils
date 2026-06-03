package dev.myrlennia237.config

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

/**
 * Cung cấp tên người dùng hiện tại cho cơ chế audit của Spring Data JPA.
 *
 * Đọc thông tin từ [SecurityContextHolder] và trả về `auth.name` nếu đã xác thực —
 * hoạt động với mọi loại [Authentication] (UserDetails, JWT, OAuth2, v.v.).
 * Trả về `"system"` nếu chưa có session bảo mật hoặc tên xác thực rỗng.
 *
 * Bean này được tự động đăng ký bởi [SpringMvcAutoConfiguration]. Để override,
 * khai báo bean [AuditorAware] riêng trong application context.
 *
 * @author <a href="https://github.com/henry0338">Myrlennia</a>
 */
class AuditorAwareImpl : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        val auth: Authentication? = SecurityContextHolder.getContext().authentication
        if (auth == null || !auth.isAuthenticated) return Optional.of("system")
        return Optional.of(auth.name.ifBlank { "system" })
    }
}
