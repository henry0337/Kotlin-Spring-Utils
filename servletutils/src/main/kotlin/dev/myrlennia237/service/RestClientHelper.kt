@file:NullMarked

package dev.myrlennia237.service

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.Predicate
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.jspecify.annotations.NullMarked
import org.jspecify.annotations.Nullable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatusCode
import org.springframework.web.client.RestClient

/**
 * HTTP client blocking bọc [RestClient] với tích hợp **Resilience4j** (Retry + Circuit Breaker).
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see <a href="https://resilience4j.readme.io/docs/getting-started">Resilience4j</a>
 */
open class RestClientHelper(private val restClient: RestClient) {
    @Autowired
    @Lazy
    @PublishedApi
    internal lateinit var self: RestClientHelper

    /**
     * **[[Java-Interoperability Variant]]**
     *
     * Gửi một HTTP GET request đến [url] rồi ánh xạ phản hồi sang kiểu [T].
     * Có tích hợp sẵn **Retry** và **Circuit Breaker** của Resilience4j.
     *
     * **Ghi chú**: [statusPredicate] và [errorHandler] đi kèm với nhau — nếu muốn xử lý lỗi
     * theo status code thì phải truyền cả hai, không thể truyền một mình [errorHandler].
     *
     * @param url            URL của endpoint cần gọi
     * @param responseType   Kiểu phản hồi mong đợi
     * @param params         Query string parameters
     * @param headers        HTTP headers bổ sung
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param errorHandler   Hàm xử lý lỗi khi [statusPredicate] khớp
     * @param T              Kiểu dữ liệu của phần thân phản hồi
     * @return Dữ liệu phản hồi kiểu [T], hoặc `null` nếu phản hồi rỗng
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
        errorHandler: @Nullable RestClient.ResponseSpec.ErrorHandler? = null
    ): T? {
        if (errorHandler != null) {
            requireNotNull(statusPredicate) { "\"statusPredicate\" không được null khi \"errorHandler\" được cung cấp!" }
        }

        val request = restClient.get()
            .uri { builder ->
                builder.path(url)
                params?.forEach { (k, v) -> builder.queryParam(k, *v) }
                builder.build()
            }
            .headers { httpHeaders -> headers?.forEach { (k, v) -> httpHeaders.set(k, v) } }

        val spec = request.retrieve()
            .let { if (statusPredicate != null && errorHandler != null) it.onStatus(statusPredicate, errorHandler) else it }

        return spec.body(responseType)
    }

    /**
     * **[[Java-Interoperability Variant]]**
     *
     * Gửi một HTTP POST request kèm [body] đến [url] rồi ánh xạ phản hồi sang kiểu [T].
     * Hoạt động tương tự [doGet] nhưng dành cho các request có phần thân.
     *
     * **Ghi chú**: Quy tắc với [statusPredicate] và [errorHandler] giống hệt [doGet] —
     * phải truyền cả hai hoặc bỏ qua cả hai.
     *
     * @param url            URL của endpoint cần gọi
     * @param body           Dữ liệu gửi kèm trong phần thân request
     * @param responseType   Kiểu phản hồi mong đợi
     * @param params         Query string parameters
     * @param headers        HTTP headers bổ sung
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param errorHandler   Hàm xử lý lỗi khi [statusPredicate] khớp
     * @param T              Kiểu dữ liệu của phần thân phản hồi
     * @param B              Kiểu dữ liệu của phần thân request
     * @return Dữ liệu phản hồi kiểu [T], hoặc `null` nếu phản hồi rỗng
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
        statusPredicate: @Nullable Predicate<HttpStatusCode>? = null,
        errorHandler: @Nullable RestClient.ResponseSpec.ErrorHandler? = null
    ): T? {
        if (errorHandler != null) {
            requireNotNull(statusPredicate) { "\"statusPredicate\" không được null khi \"errorHandler\" được cung cấp!" }
        }

        val request = restClient.post()
            .uri { builder ->
                builder.path(url)
                params?.forEach { (k, v) -> builder.queryParam(k, *v) }
                builder.build()
            }
            .headers { httpHeaders -> headers?.forEach { (k, v) -> httpHeaders.set(k, v) } }
            .body(body)

        val spec = request.retrieve()
            .let { if (statusPredicate != null && errorHandler != null) it.onStatus(statusPredicate, errorHandler) else it }

        return spec.body(responseType)
    }

    /**
     * **[[Kotlin Variant]]**
     *
     * Phiên bản Kotlin của [doGet] — nhờ `reified`, không cần truyền [ParameterizedTypeReference]
     * thủ công, kiểu [T] được suy diễn tự động tại compile-time.
     *
     * **Ghi chú**: Chỉ dành cho **Kotlin**, không khả dụng từ Java.
     *
     * @param url            URL của endpoint cần gọi
     * @param params         Query string parameters
     * @param headers        HTTP headers bổ sung
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param errorHandler   Hàm xử lý lỗi khi [statusPredicate] khớp
     * @param T              Kiểu dữ liệu của phần thân phản hồi
     * @return Dữ liệu phản hồi kiểu [T], hoặc `null` nếu phản hồi rỗng
     * @author <a href="https://github.com/henry0337">Myrlennia</a>
     */
    @KotlinVariant
    @JvmSynthetic
    inline fun <reified T : Any> get(
        url: String,
        params: Map<String, Array<Any>>? = null,
        headers: Map<String, String?>? = null,
        noinline statusPredicate: Predicate<HttpStatusCode>? = null,
        errorHandler: RestClient.ResponseSpec.ErrorHandler? = null
    ): T? = self.doGet(
        url,
        responseType = object : ParameterizedTypeReference<T>() {},
        params,
        headers,
        statusPredicate,
        errorHandler
    )

    /**
     * **[[Kotlin Variant]]**
     *
     * Phiên bản Kotlin của [doPost] — tương tự [get], kiểu [T] và [B] được suy diễn tự động
     * nhờ `reified`.
     *
     * **Ghi chú**: Chỉ dành cho **Kotlin**, không khả dụng từ Java.
     *
     * @param url            URL của endpoint cần gọi
     * @param body           Dữ liệu gửi kèm trong phần thân request
     * @param params         Query string parameters
     * @param headers        HTTP headers bổ sung
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param errorHandler   Hàm xử lý lỗi khi [statusPredicate] khớp
     * @param T              Kiểu dữ liệu của phần thân phản hồi
     * @param B              Kiểu dữ liệu của phần thân request
     * @return Dữ liệu phản hồi kiểu [T], hoặc `null` nếu phản hồi rỗng
     * @author <a href="https://github.com/henry0337">Myrlennia</a>
     */
    @KotlinVariant
    @JvmSynthetic
    inline fun <reified T : Any, reified B : Any> post(
        url: String,
        body: B,
        params: Map<String, Array<Any>>? = null,
        headers: Map<String, String?>? = null,
        noinline statusPredicate: Predicate<HttpStatusCode>? = null,
        errorHandler: RestClient.ResponseSpec.ErrorHandler? = null
    ): T? = self.doPost(
        url,
        body,
        responseType = object : ParameterizedTypeReference<T>() {},
        params,
        headers,
        statusPredicate,
        errorHandler
    )

    @Suppress("UNUSED_PARAMETER", "unused")
    protected fun <T : Any> retryFallback(ex: Throwable): T = throw ex

    @Suppress("UNUSED_PARAMETER", "unused")
    protected fun <T : Any> circuitBreakerFallback(ex: Throwable): T = throw ex
}
