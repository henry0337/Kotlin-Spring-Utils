package dev.myrlennia237.service

import dev.myrlennia237.JavaDuration
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import kotlin.time.Duration
import kotlin.time.toJavaDuration

/**
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @sample dev.myrlennia237.sample.FooComponent1
 */
public class RedisService(redisTemplate: StringRedisTemplate) {
    private val redisOps: ValueOperations<String, String> = redisTemplate.opsForValue()

    /**
     * Lưu vào Redis với cặp [key]-[value] tương ứng, với thời lượng hợp lệ trong [duration].
     *
     * **Ghi chú**: Giá trị của hàm này có thể được **bỏ qua**, nếu không có nhu cầu sử dụng.
     *
     * @param key      Khóa được lưu trong Redis
     * @param value    Giá trị được lưu vào khóa đó
     * @param duration Thời gian tồn tại của cặp khóa-giá trị này, mặc định là vô thời hạn ([Duration.INFINITE])
     * @return `true` nếu ghi thành công, `false` nếu giá trị [duration] không hợp lệ hoặc lỗi khác.
     * @see ValueOperations.set
     * @see <a href="https://redis.io/docs/latest/commands/set/">Lệnh Redis: SET</a>
     */
    @JvmOverloads
    public fun set(
        key: String,
        value: String,
        duration: JavaDuration? = Duration.INFINITE.toJavaDuration()
    ): Boolean {
        return when {
            duration != null && (duration.isZero || duration.isNegative) -> false
            duration != null -> { redisOps.set(key, value, duration); true }
            else -> { redisOps.set(key, value); true }
        }
    }

    /**
     * Lấy ra giá trị được lưu trong [key].
     *
     * @param key Khóa được lưu trong Redis
     * @return Giá trị được lưu trong [key] nếu tồn tại, hoặc `null` nếu không có.
     */
    public fun get(key: String): String? = redisOps.get(key)
}