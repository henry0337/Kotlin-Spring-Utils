package dev.myrlennia237.service

import dev.myrlennia237.JavaDuration
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import reactor.core.publisher.Mono
import kotlin.time.Duration
import kotlin.time.toJavaDuration

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public class ReactiveRedisService(redisTemplate: ReactiveStringRedisTemplate) {
    private val redisOps: ReactiveValueOperations<String, String> = redisTemplate.opsForValue()

    /**
     * Lưu vào Redis với cặp [key]-[value] tương ứng, với hiệu lực sử dụng được giới hạn trong [duration].
     * @param key      Tên khóa
     * @param value    Giá trị được thiết lập cho khóa đó
     * @param duration Thời gian tồn tại của cặp khóa-giá trị này, mặc định là vô thời hạn ([Duration.INFINITE])
     * @return `true` nếu ghi thành công, `false` nếu giá trị [duration] không hợp lệ hoặc lỗi khác.
     */
    @JvmOverloads
    public fun set(
        key: String,
        value: String,
        duration: JavaDuration = Duration.INFINITE.toJavaDuration()
    ): Mono<Boolean> = if (duration.isZero || duration.isNegative) {
        Mono.just(false)
    } else {
        redisOps.set(key, value, duration)
    }
}