package dev.myrlennia237.contract

import java.util.UUID

/**
 * Contract cho principal của người dùng đã xác thực, yêu cầu expose UUID thực từ cơ sở dữ liệu.
 *
 * Implement interface này trên `UserDetails` (hoặc principal tương ứng) của ứng dụng để
 * tích hợp với cơ chế auditing ([org.springframework.data.domain.AuditorAware]) của thư viện.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public interface UserPrincipal {
    /**
     * UUID thực của người dùng trong cơ sở dữ liệu.
     */
    public val userId: UUID
}