package dev.myrlennia237.service

import dev.myrlennia237.JavaDuration
import dev.myrlennia237.annotation.KotlinVariant
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import org.springframework.data.redis.core.getAndAwait
import reactor.core.publisher.Mono
import kotlin.time.Duration
import kotlin.time.toJavaDuration

/**
 * Redis helper reactive, bọc các thao tác phổ biến của
 * [ReactiveStringRedisTemplate][org.springframework.data.redis.core.ReactiveStringRedisTemplate].
 *
 * Được auto-configure khi bean [ReactiveStringRedisTemplate][org.springframework.data.redis.core.ReactiveStringRedisTemplate]
 * có mặt trong context. Hỗ trợ cả Java API (trả về [reactor.core.publisher.Mono]) lẫn
 * Kotlin coroutine API (hàm `suspend`).
 *
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
        duration: JavaDuration = Duration.INFINITE.toJavaDuration(),
    ): Mono<Boolean> = if (duration.isZero || duration.isNegative) {
        Mono.just(false)
    } else {
        redisOps.set(key, value, duration)
    }

    /**
     * Đọc ra giá trị được gán vào [key] tương ứng.
     * @param key Tên khóa cần đọc
     * @return Giá trị được lưu trong [key] nếu tồn tại, không thì trả về [Mono.empty].
     */
    public fun get(key: String): Mono<String> = redisOps.get(key)

    /**
     * Đọc ra giá trị được gán vào [key] tương ứng.
     *
     * (**Ghi chú**: Hàm này chỉ khả dụng cho các **API Kotlin**.)
     * @param key Tên khóa cần đọc
     * @return Giá trị được lưu trong [key] nếu tồn tại, hoặc `null` nếu không có giá trị được lưu.
     */
    @KotlinVariant
    @JvmSynthetic
    public suspend fun awaitGet(key: String): String? = redisOps.getAndAwait(key)
}