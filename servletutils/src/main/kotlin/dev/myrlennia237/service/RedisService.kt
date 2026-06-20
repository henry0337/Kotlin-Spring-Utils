package dev.myrlennia237.service

import dev.myrlennia237.JavaDuration
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
open class RedisService(redisTemplate: StringRedisTemplate) {
    private val redisOps: ValueOperations<String, String> = redisTemplate.opsForValue()

    /**
     * Lưu vào Redis với cặp [key]-[value] tương ứng, với hiệu lực sử dụng được giới hạn trong khoảng [duration].
     * @param key      Tên khóa
     * @param value    Giá trị được thiết lập cho khóa đó
     * @param duration Thời gian tồn tại của cặp khóa-giá trị này, mặc định là `null` (vô thời hạn)
     * @return `true` nếu ghi thành công, `false` nếu giá trị [duration] không hợp lệ hoặc lỗi khác.
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
     * Đọc ra giá trị được gán vào [key] tương ứng.
     * @param key Tên khóa cần đọc
     * @return Giá trị được lưu trong [key] nếu tồn tại, hoặc `null` nếu không có.
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