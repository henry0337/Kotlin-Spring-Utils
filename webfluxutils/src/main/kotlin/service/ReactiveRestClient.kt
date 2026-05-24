@file:NullMarked

package service

import annotation.KotlinVariant
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.jspecify.annotations.NullMarked
import org.jspecify.annotations.Nullable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatusCode
import org.springframework.context.annotation.Lazy
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import utils.Function
import utils.Predicate

open class ReactiveRestClient(private val webClient: WebClient) {
    @Autowired
    @Lazy
    @PublishedApi
    internal lateinit var self: ReactiveRestClient

    /**
     * **[[Java-Interoperability Variant]]**
     *
     * Gửi một HTTP GET request đến [url] rồi ánh xạ phản hồi sang kiểu [T].
     * Có tích hợp sẵn **Retry** và **Circuit Breaker** của Resilience4j.
     *
     * **Ghi chú**: [statusPredicate] và [responseHandler] đi kèm với nhau — nếu muốn xử lý lỗi
     * theo status code thì phải truyền cả hai, không thể truyền một mình [responseHandler].
     *
     * @param url URL của endpoint cần gọi
     * @param responseType Kiểu phản hồi mong đợi
     * @param params Query string parameters
     * @param headers HTTP headers bổ sung
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param responseHandler Hàm xử lý lỗi khi [statusPredicate] khớp
     * @param T Kiểu dữ liệu của phần thân phản hồi
     * @return [Mono] bọc dữ liệu phản hồi kiểu [T]
     * @author <a href="https://github.com/henry0337">Myrlennia</a>
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
            .let { if (statusPredicate != null && responseHandler != null) it.onStatus(statusPredicate, responseHandler) else it }

        return spec.bodyToMono(responseType)
    }

    /**
     * **[[Reactive POST]]**
     *
     * Gửi một HTTP POST request kèm [body] đến [url] rồi ánh xạ phản hồi sang kiểu [T].
     * Hoạt động tương tự [doGet] nhưng dành cho các request có phần thân.
     *
     * **Ghi chú**: Quy tắc với [statusPredicate] và [responseHandler] giống hệt [doGet] —
     * phải truyền cả hai hoặc bỏ qua cả hai.
     *
     * @param url URL của endpoint cần gọi
     * @param body Dữ liệu gửi kèm trong phần thân request
     * @param responseType Kiểu phản hồi mong đợi
     * @param params Query string parameters
     * @param headers HTTP headers bổ sung
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param responseHandler Hàm xử lý lỗi khi [statusPredicate] khớp
     * @param T Kiểu dữ liệu của phần thân phản hồi
     * @param B Kiểu dữ liệu của phần thân request
     * @return [Mono] bọc dữ liệu phản hồi kiểu [T]
     * @author <a href="https://github.com/henry0337">Myrlennia</a>
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
            .let { if (statusPredicate != null && responseHandler != null) it.onStatus(statusPredicate, responseHandler) else it }

        return spec.bodyToMono(responseType)
    }

    /**
     * **[[Coroutine GET]]**
     *
     * Phiên bản coroutine của [doGet], trả về trực tiếp [T] thay vì [Mono].
     * Nhờ `reified`, không cần truyền [ParameterizedTypeReference] thủ công — kiểu [T] được
     * suy diễn tự động tại compile-time.
     *
     * **Ghi chú**: Chỉ dành cho **Kotlin**, không khả dụng từ Java.
     *
     * @param url URL của endpoint cần gọi
     * @param params Query string parameters
     * @param headers HTTP headers bổ sung
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param responseHandler Hàm xử lý lỗi khi [statusPredicate] khớp
     * @param T Kiểu dữ liệu của phần thân phản hồi
     * @return Dữ liệu phản hồi kiểu [T], hoặc `null` nếu phản hồi rỗng
     * @author <a href="https://github.com/henry0337">Myrlennia</a>
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
     * **[[Coroutine POST]]**
     *
     * Phiên bản coroutine của [doPost], trả về trực tiếp [T] thay vì [Mono].
     * Tương tự [awaitGet], kiểu [T] và [B] đều được suy diễn tự động nhờ `reified`.
     *
     * **Ghi chú**: Chỉ dành cho **Kotlin**, không khả dụng từ Java.
     *
     * @param url URL của endpoint cần gọi
     * @param body Dữ liệu gửi kèm trong phần thân request
     * @param params Query string parameters
     * @param headers HTTP headers bổ sung
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param responseHandler Hàm xử lý lỗi khi [statusPredicate] khớp
     * @param T Kiểu dữ liệu của phần thân phản hồi
     * @param B Kiểu dữ liệu của phần thân request
     * @return Dữ liệu phản hồi kiểu [T], hoặc `null` nếu phản hồi rỗng
     * @author <a href="https://github.com/henry0337">Myrlennia</a>
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

    @Suppress("UNUSED")
    private fun <T : Any> retryFallback(ex: Throwable): Mono<T> = Mono.error(ex)

    @Suppress("UNUSED")
    private fun <T : Any> circuitBreakerFallback(ex: Throwable): Mono<T> = Mono.error(ex)
}
