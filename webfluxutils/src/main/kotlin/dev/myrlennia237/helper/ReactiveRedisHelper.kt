package dev.myrlennia237.helper

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.utils.JavaDuration
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import org.springframework.data.redis.core.getAndAwait
import reactor.core.publisher.Mono
import kotlin.time.Duration
import kotlin.time.toJavaDuration

/**
 * Helper bọc [ReactiveRedisTemplate] với các thao tác đọc/ghi thông dụng trên Redis.
 *
 * Bean này được tự động đăng ký bởi
 * [dev.myrlennia237.config.SpringUtilsAutoConfiguration] khi có [ReactiveRedisTemplate]
 * trong Spring context. Có thể extend class này để thêm các thao tác tùy chỉnh.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
open class ReactiveRedisHelper(redisTemplate: ReactiveRedisTemplate<String, String>) {
    private val redisOps: ReactiveValueOperations<String, String> = redisTemplate.opsForValue()

    /**
     * Ghi [value] vào [key] với thời gian sống [duration].
     *
     * Nếu [duration] là `0` hoặc âm, thao tác bị bỏ qua và trả về `Mono<false>`.
     * Mặc định [duration] là vô hạn — key sẽ không hết hạn trừ khi bị xóa thủ công.
     *
     * @param key      Khóa Redis
     * @param value    Giá trị cần lưu
     * @param duration Thời gian sống của key; mặc định là vô hạn
     * @return `Mono<true>` nếu ghi thành công, `Mono<false>` nếu [duration] không hợp lệ
     */
    @JvmOverloads
    fun set(
        key: String,
        value: String,
        duration: JavaDuration = Duration.INFINITE.toJavaDuration(),
    ): Mono<Boolean> = if (duration.isZero || duration.isNegative) {
        Mono.just(false)
    } else {
        redisOps.set(key, value, duration)
    }

    /**
     * Đọc toàn bộ giá trị của [key] bằng lệnh Redis `GET`.
     *
     * @param key Khóa Redis cần đọc
     * @return `Mono` chứa giá trị, hoặc rỗng nếu key không tồn tại
     */
    fun get(key: String): Mono<String> = redisOps.get(key)

    /**
     * Đọc một đoạn con của giá trị [key] bằng lệnh Redis `GETRANGE`.
     *
     * @param key   Khóa Redis cần đọc
     * @param start Vị trí bắt đầu (inclusive, 0-based)
     * @param end   Vị trí kết thúc (inclusive); `-1` tương đương cuối chuỗi
     * @return `Mono` chứa chuỗi con tương ứng
     */
    fun getRange(key: String, start: Long = 0, end: Long = -1): Mono<String> =
        redisOps.get(key, start, end)

    /**
     * Phiên bản coroutine của [get] — trả về trực tiếp [String] thay vì [Mono].
     *
     * @param key Khóa Redis cần đọc
     * @return Giá trị nếu key tồn tại, hoặc `null`
     */
    @KotlinVariant
    @JvmSynthetic
    suspend fun awaitGet(key: String): String? = redisOps.getAndAwait(key)
}
