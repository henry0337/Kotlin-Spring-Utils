package dev.myrlennia237.helper

import dev.myrlennia237.JavaDuration
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations

/**
 * Helper bọc [StringRedisTemplate] với các thao tác đọc/ghi thông dụng trên Redis.
 *
 * Bean này được tự động đăng ký bởi
 * [dev.myrlennia237.config.SpringMvcAutoConfiguration] khi có [StringRedisTemplate]
 * trong Spring context. Có thể extend class này để thêm các thao tác tùy chỉnh.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
open class RedisHelper(redisTemplate: StringRedisTemplate) {
    private val redisOps: ValueOperations<String, String> = redisTemplate.opsForValue()

    /**
     * Ghi [value] vào [key] với thời gian sống tùy chọn.
     *
     * Nếu [duration] là `0` hoặc âm, thao tác bị bỏ qua và trả về `false`.
     * Mặc định [duration] là `null` — key sẽ không hết hạn trừ khi bị xóa thủ công.
     *
     * @param key      Khóa Redis
     * @param value    Giá trị cần lưu
     * @param duration Thời gian sống của key; mặc định là `null` (vô hạn)
     * @return `true` nếu ghi thành công, `false` nếu [duration] không hợp lệ
     */
    @JvmOverloads
    fun set(key: String, value: String, duration: JavaDuration? = null): Boolean {
        return when {
            duration != null && (duration.isZero || duration.isNegative) -> false
            duration != null -> { redisOps.set(key, value, duration); true }
            else -> { redisOps.set(key, value); true }
        }
    }

    /**
     * Đọc toàn bộ giá trị của [key] bằng lệnh Redis `GET`.
     *
     * @param key Khóa Redis cần đọc
     * @return Giá trị nếu key tồn tại, hoặc `null`
     */
    fun get(key: String): String? = redisOps.get(key)

    /**
     * Đọc một đoạn con của giá trị [key] bằng lệnh Redis `GETRANGE`.
     *
     * @param key   Khóa Redis cần đọc
     * @param start Vị trí bắt đầu (inclusive, 0-based)
     * @param end   Vị trí kết thúc (inclusive); `-1` tương đương cuối chuỗi
     * @return Chuỗi con tương ứng, hoặc `null` nếu key không tồn tại
     */
    @JvmOverloads
    fun getRange(key: String, start: Long = 0, end: Long = -1): String? =
        redisOps.get(key, start, end)
}
