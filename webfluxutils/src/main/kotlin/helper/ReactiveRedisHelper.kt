package helper

import annotation.KotlinVariant
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import org.springframework.data.redis.core.getAndAwait
import reactor.core.publisher.Mono
import utils.JavaDuration
import kotlin.time.Duration
import kotlin.time.toJavaDuration

/**
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
open class ReactiveRedisHelper(redisTemplate: ReactiveRedisTemplate<String, String>) {
    private val redisOps: ReactiveValueOperations<String, String> = redisTemplate.opsForValue()

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

    @JvmOverloads
    fun get(
        key: String,
        start: Long = 0,
        end: Long = Long.MAX_VALUE
    ): Mono<String> = redisOps.get(key, start, end)

    @KotlinVariant
    suspend fun awaitGet(key: String): String? {
        return redisOps.getAndAwait(key)
    }
}
