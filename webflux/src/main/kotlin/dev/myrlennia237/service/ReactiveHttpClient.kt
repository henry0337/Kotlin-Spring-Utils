@file:NullMarked

package dev.myrlennia237.service

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.Function
import dev.myrlennia237.Predicate
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.jspecify.annotations.NullMarked
import org.jspecify.annotations.Nullable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

/**
 * Một Reactive HTTP Client dùng để gọi tới các API bên thứ 3.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
open class ReactiveHttpClient(private val webClient: WebClient) {
    @Autowired
    @Lazy
    @PublishedApi
    internal lateinit var self: ReactiveHttpClient

    /**
     * Gửi một HTTP GET request đến [url] rồi trả về phản hồi với kiểu [T] tương ứng.
     *
     * @param url URL của endpoint cần gọi
     * @param responseType Kiểu phản hồi mong đợi
     * @param params Tham số URL cần truyền vào
     * @param headers Các header HTTP cần thêm vào request
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param responseHandler Hàm xử lý khi [statusPredicate] khớp
     * @param T Kiểu phản hồi mong đợi
     * @return Dữ liệu phản hồi mong muốn nếu thành công, hoặc empty nếu thất bại (được bọc bởi một [Mono]).
     * @author <a href="https://github.com/henry0337">Muharux</a>
     * @see <a href="https://resilience4j.readme.io/docs/getting-started">Resilience4j</a>
     */
    @Retry(name = "unwrapGet", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "unwrapGet", fallbackMethod = "circuitBreakerFallback")
    open fun <T : Any> doGet(
        url: String,
        responseType: ParameterizedTypeReference<T>,
        params: @Nullable Map<String, Array<Any>>? = null,
        headers: @Nullable Map<String, @Nullable String?>? = null,
        statusPredicate: @Nullable Predicate<HttpStatusCode>? = null,
        responseHandler: @Nullable Function<ClientResponse, Mono<out Throwable>>? = null
    ): Mono<T> {
        if (responseHandler != null) {
            requireNotNull(statusPredicate) { "\"statusPredicate\" không được null khi \"responseHandler\" được cung cấp!" }
        }

        val request = webClient.get()
            .uri {
                it.path(url)
                params?.forEach { (k, v) -> it.queryParam(k, v) }
                it.build()
            }
            .headers { headers?.forEach { (k, v) -> it.set(k, v) } }

        val spec = request.retrieve()
            .let {
                if (statusPredicate != null && responseHandler != null)
                    it.onStatus(statusPredicate, responseHandler)
                else it
            }

        return spec.bodyToMono(responseType)
    }

    /**
     * Gửi một HTTP POST request đến [url] rồi trả về phản hồi với kiểu [T] tương ứng.
     *
     * @param url URL của endpoint cần gọi
     * @param body Dữ liệu gửi kèm trong request body
     * @param responseType Kiểu phản hồi mong đợi
     * @param params Tham số URL cần truyền vào
     * @param headers Các header HTTP cần thêm vào request
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param responseHandler Hàm xử lý khi [statusPredicate] khớp
     * @param T Kiểu phản hồi mong đợi
     * @param B Kiểu dữ liệu đầu vào của request body
     * @return Dữ liệu phản hồi mong muốn nếu thành công, hoặc empty nếu thất bại (được bọc bởi một [Mono]).
     * @author <a href="https://github.com/henry0337">Muharux</a>
     * @see <a href="https://resilience4j.readme.io/docs/getting-started">Resilience4j</a>
     */
    @Retry(name = "unwrapPost", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "unwrapPost", fallbackMethod = "circuitBreakerFallback")
    open fun <T : Any, B : Any> doPost(
        url: String,
        body: B,
        responseType: ParameterizedTypeReference<T>,
        params: @Nullable Map<String, Array<Any>>? = null,
        headers: @Nullable Map<String, @Nullable String?>? = null,
        statusPredicate: Predicate<HttpStatusCode>? = null,
        responseHandler: Function<ClientResponse, Mono<out Throwable>>? = null
    ): Mono<T> {
        if (responseHandler != null) {
            requireNotNull(statusPredicate) { "\"statusPredicate\" không được null khi \"responseHandler\" được cung cấp!" }
        }

        val request = webClient.post()
            .uri {
                it.path(url)
                params?.forEach { (k, v) -> it.queryParam(k, v) }
                it.build()
            }
            .headers { headers?.forEach { (k, v) -> it.set(k, v) } }
            .bodyValue(body)

        val spec = request.retrieve()
            .let {
                if (statusPredicate != null && responseHandler != null) it.onStatus(statusPredicate, responseHandler)
                else it
            }

        return spec.bodyToMono(responseType)
    }

    /**
     * Gửi một HTTP GET request đến [url] rồi trả về phản hồi với kiểu [T] tương ứng.
     *
     * (**Ghi chú**: Hàm này chỉ khả dụng cho các **API Kotlin**.)
     * @param url URL của endpoint cần gọi
     * @param params Tham số URL cần truyền vào
     * @param headers Các header HTTP cần thêm vào request
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param responseHandler Hàm xử lý khi [statusPredicate] khớp
     * @param T Kiểu dữ liệu của phần thân phản hồi
     * @return Dữ liệu phản hồi mong muốn nếu thành công, hoặc `null` nếu thất bại.
     * @author <a href="https://github.com/henry0337">Muharux</a>
     */
    @KotlinVariant
    @JvmSynthetic
    suspend inline fun <reified T : Any> awaitGet(
        url: String,
        params: Map<String, Array<Any>>? = null,
        headers: Map<String, String?>? = null,
        noinline statusPredicate: Predicate<HttpStatusCode>? = null,
        noinline responseHandler: Function<ClientResponse, Mono<out Throwable>>? = null
    ): T? = self.doGet(
        url,
        responseType = object : ParameterizedTypeReference<T>() {},
        params,
        headers,
        statusPredicate,
        responseHandler
    ).awaitSingleOrNull()

    /**
     * Gửi một HTTP POST request đến [url] rồi trả về phản hồi với kiểu [T] tương ứng.
     *
     * (**Ghi chú**: Hàm này chỉ khả dụng cho các **API Kotlin**.)
     * @param url URL của endpoint cần gọi
     * @param body Dữ liệu gửi kèm trong request body
     * @param params Tham số URL cần truyền vào
     * @param headers Các header HTTP cần thêm vào request
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param responseHandler Hàm xử lý khi [statusPredicate] khớp
     * @param T Kiểu phản hồi mong đợi
     * @param B Kiểu dữ liệu đầu vào của request body
     * @return Dữ liệu phản hồi mong muốn nếu thành công, hoặc `null` nếu thất bại.
     * @author <a href="https://github.com/henry0337">Muharux</a>
     */
    @KotlinVariant
    @JvmSynthetic
    suspend inline fun <reified T : Any, reified B : Any> awaitPost(
        url: String,
        body: B,
        params: Map<String, Array<Any>>? = null,
        headers: Map<String, String?>? = null,
        noinline statusPredicate: Predicate<HttpStatusCode>? = null,
        noinline responseHandler: Function<ClientResponse, Mono<out Throwable>>? = null
    ): T? = self.doPost(
        url,
        body,
        responseType = object : ParameterizedTypeReference<T>() {},
        params,
        headers,
        statusPredicate,
        responseHandler
    ).awaitSingleOrNull()

    @Suppress("unused")
    protected fun <T : Any> retryFallback(ex: Throwable): Mono<T> = Mono.error(ex)

    @Suppress("unused")
    protected fun <T : Any> circuitBreakerFallback(ex: Throwable): Mono<T> = Mono.error(ex)
}
