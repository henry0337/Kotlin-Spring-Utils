package dev.myrlennia237.service

import dev.myrlennia237.JavaDuration
import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.extension.await
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import org.springframework.data.redis.core.deleteAndAwait
import org.springframework.data.redis.core.getAndAwait
import org.springframework.data.redis.core.setAndAwait
import reactor.core.publisher.Mono
import kotlin.time.Duration
import kotlin.time.toJavaDuration

/**
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
public class ReactiveRedisService(redisTemplate: ReactiveStringRedisTemplate) {
    private val redisOps: ReactiveValueOperations<String, String> = redisTemplate.opsForValue()

    /**
     * Lấy ra giá trị được lưu trong [key].
     *
     * @param key Khóa được lưu trong Redis
     * @return [Mono] phát ra giá trị được lưu trong [key] được chỉ định, hoặc [Mono.empty] nếu như không tìm thấy.
     * @see ReactiveValueOperations.get
     */
    public fun get(key: String): Mono<String> = redisOps.get(key)

    /**
     * Lưu vào Redis với cặp [key]-[value] tương ứng, với thời lượng hợp lệ trong [duration].
     *
     * **Ghi chú**: Giá trị của hàm này có thể được **bỏ qua**, nếu không có nhu cầu sử dụng.
     *
     * @param key      Khóa được lưu trong Redis
     * @param value    Giá trị được lưu vào khóa đó
     * @param duration Thời gian tồn tại của cặp khóa-giá trị này, mặc định là vô thời hạn ([Duration.INFINITE])
     * @return [Mono] phát ra giá trị `true` nếu ghi thành công, `false` nếu giá trị [duration] không hợp lệ hoặc lỗi khác.
     * @see ReactiveValueOperations.set
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

    /**
     * Loại bỏ [key] được chỉ định.
     *
     * **Ghi chú**: Giá trị của hàm này có thể được **bỏ qua**, nếu không có nhu cầu sử dụng.
     *
     * @param key Khóa sẽ bị xóa khỏi Redis
     * @return [Mono] phát ra giá trị `true`/`false` tương ứng biểu thị hành động xóa **thành công/thất bại**.
     * @see ReactiveValueOperations.delete
     */
    public fun delete(key: String): Mono<Boolean> = redisOps.delete(key)

    /**
     * Vô hiệu [key] được chỉ định bằng cách áp đặt thời lượng vô hiệu được thiết lập qua [duration], đồng thời trả về
     * giá trị được lưu trong [key] đó.
     *
     * **Ghi chú**: Giá trị của hàm này có thể được **bỏ qua**, nếu không có nhu cầu sử dụng.
     *
     * @param key       Khóa được lưu trong Redis
     * @param duration  Thời lượng vô hiệu sẽ diễn ra, mặc định là vô thời hạn ([Duration.INFINITE])
     * @return [Mono] phát ra giá trị được lưu trong [key] chỉ định.
     * @see ReactiveValueOperations.getAndExpire
     */
    @JvmOverloads
    public fun expire(
        key: String,
        duration: JavaDuration = Duration.INFINITE.toJavaDuration()
    ): Mono<String> = redisOps.getAndExpire(key, duration)

    /**
     * Lấy ra giá trị được lưu trong [key].
     *
     * @param key Khóa được lưu trong Redis
     * @return Giá trị được lưu trong [key] được chỉ định.
     * @see ReactiveRedisService.get
     */
    @KotlinVariant
    @JvmSynthetic
    public suspend fun getAndAwait(key: String): String? = redisOps.getAndAwait(key)

    /**
     * Lưu vào Redis với cặp [key]-[value] tương ứng, với thời lượng hợp lệ trong [duration].
     *
     * **Ghi chú**: Giá trị của hàm này có thể được **bỏ qua**, nếu không có nhu cầu sử dụng.
     *
     * @param key      Khóa được lưu trong Redis
     * @param value    Giá trị được lưu vào khóa đó
     * @param duration Thời gian tồn tại của cặp khóa-giá trị này, mặc định là vô thời hạn ([Duration.INFINITE])
     * @return `true` nếu ghi thành công, `false` nếu giá trị [duration] không hợp lệ hoặc lỗi khác.
     * @see ReactiveRedisService.set
     */
    @KotlinVariant
    @JvmSynthetic
    @IgnorableReturnValue
    public suspend fun setAndAwait(
        key: String,
        value: String,
        duration: JavaDuration = Duration.INFINITE.toJavaDuration()
    ): Boolean = !(duration.isZero || duration.isNegative) && redisOps.setAndAwait(key, value, duration)

    /**
     * Loại bỏ [key] được chỉ định.
     *
     * **Ghi chú**: Giá trị của hàm này có thể được **bỏ qua**, nếu không có nhu cầu sử dụng.
     *
     * @param key Khóa sẽ bị xóa khỏi Redis
     * @return `true`/`false` tương ứng biểu thị hành động xóa **thành công/thất bại**.
     * @see ReactiveRedisService.delete
     */
    @KotlinVariant
    @JvmSynthetic
    @IgnorableReturnValue
    public suspend fun deleteAndAwait(key: String): Boolean = redisOps.deleteAndAwait(key)

    /**
     * Vô hiệu [key] được chỉ định bằng cách áp đặt thời lượng vô hiệu được thiết lập qua [duration], đồng thời trả về
     * giá trị được lưu trong [key] đó.
     *
     * **Ghi chú**: Giá trị của hàm này có thể được **bỏ qua**, nếu không có nhu cầu sử dụng.
     *
     * @param key       Khóa được lưu trong Redis
     * @param duration  Thời lượng vô hiệu sẽ được áp dụng, mặc định là vô thời hạn ([Duration.INFINITE])
     * @return Giá trị được lưu trong [key] chỉ định hoặc `null`.
     * @see ReactiveRedisService.expire
     */
    @KotlinVariant
    @JvmSynthetic
    @IgnorableReturnValue
    public suspend fun expireAndAwait(
        key: String,
        duration: JavaDuration = Duration.INFINITE.toJavaDuration()
    ): String? = redisOps.getAndExpire(key, duration).await()
}