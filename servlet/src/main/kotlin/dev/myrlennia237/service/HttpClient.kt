@file:NullMarked

package dev.myrlennia237.service

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.Predicate
import dev.myrlennia237.exception.HttpClientException
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
 * Một HTTP Client dùng để gọi tới các API bên thứ 3.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public open class HttpClient(private val restClient: RestClient) {
    @Autowired
    @Lazy
    @PublishedApi
    internal lateinit var self: HttpClient

    /**
     * Gửi một HTTP GET request đến [url] rồi trả về phản hồi với kiểu [T] tương ứng.
     *
     * @param url URL của endpoint cần gọi
     * @param responseType Kiểu phản hồi mong đợi
     * @param params Tham số URL cần truyền vào
     * @param headers Các header HTTP cần thêm vào request
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param errorHandler Hàm xử lý khi [statusPredicate] khớp; chạy đồng bộ trên chính luồng gọi (blocking).
     * @param T Kiểu phản hồi mong đợi
     * @return Dữ liệu phản hồi mong muốn nếu thành công, hoặc `null` nếu thất bại.
     * @throws IllegalArgumentException nếu [errorHandler] được cung cấp nhưng [statusPredicate] là `null`.
     * @throws dev.myrlennia237.exception.HttpClientException nếu lời gọi thất bại sau khi retry, hoặc khi circuit
     *   breaker đang mở; nguyên nhân gốc (exception của Spring/Resilience4j) được giữ qua `cause`.
     * @author <a href="https://github.com/henry0337">Muharux</a>
     * @see <a href="https://resilience4j.readme.io/docs/getting-started">Resilience4j</a>
     */
    @Retry(name = "unwrapGet", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "unwrapGet", fallbackMethod = "circuitBreakerFallback")
    public open fun <T : Any> doGet(
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
     * Phiên bản rút gọn của [doGet] cho lệnh gọi từ **Java** không cần `params`/`headers`/xử lý lỗi tuỳ biến —
     * tránh phải truyền `null` thủ công cho từng tham số còn lại. Đi qua [self] (không gọi trực tiếp [doGet])
     * để lệnh gọi vẫn xuyên qua AOP proxy, giữ nguyên `@Retry`/`@CircuitBreaker`.
     * @author <a href="https://github.com/henry0337">Muharux</a>
     */
    public fun <T : Any> doGet(url: String, responseType: ParameterizedTypeReference<T>): T? =
        self.doGet(url, responseType, null, null, null, null)

    /**
     * Phiên bản rút gọn của [doGet] cho lệnh gọi từ **Java** kèm `params`/`headers` nhưng không cần xử lý lỗi
     * tuỳ biến. Đi qua [self] để giữ nguyên `@Retry`/`@CircuitBreaker` như [doGet] gốc.
     * @author <a href="https://github.com/henry0337">Muharux</a>
     */
    public fun <T : Any> doGet(
        url: String,
        responseType: ParameterizedTypeReference<T>,
        params: @Nullable Map<String, Array<Any>>?,
        headers: @Nullable Map<String, @Nullable String?>?
    ): T? = self.doGet(url, responseType, params, headers, null, null)

    /**
     * Gửi một HTTP POST request đến [url] rồi trả về phản hồi với kiểu [T] tương ứng.
     *
     * @param url URL của endpoint cần gọi
     * @param body Dữ liệu gửi kèm trong request body
     * @param responseType Kiểu phản hồi mong đợi
     * @param params Tham số URL cần truyền vào
     * @param headers Các header HTTP cần thêm vào request
     * @param statusPredicate Điều kiện để lọc status code lỗi cần xử lý
     * @param errorHandler Hàm xử lý khi [statusPredicate] khớp; chạy đồng bộ trên chính luồng gọi (blocking).
     * @param T Kiểu phản hồi mong đợi
     * @param B Kiểu dữ liệu đầu vào của request body
     * @return Dữ liệu phản hồi mong muốn nếu thành công, hoặc `null` nếu thất bại.
     * @throws IllegalArgumentException nếu [errorHandler] được cung cấp nhưng [statusPredicate] là `null`.
     * @throws dev.myrlennia237.exception.HttpClientException nếu lời gọi thất bại sau khi retry, hoặc khi circuit
     *   breaker đang mở; nguyên nhân gốc (exception của Spring/Resilience4j) được giữ qua `cause`.
     * @author <a href="https://github.com/henry0337">Muharux</a>
     * @see <a href="https://resilience4j.readme.io/docs/getting-started">Resilience4j</a>
     */
    @Retry(name = "unwrapPost", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "unwrapPost", fallbackMethod = "circuitBreakerFallback")
    public open fun <T : Any, B : Any> doPost(
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
     * Phiên bản rút gọn của [doPost] cho lệnh gọi từ **Java** không cần `params`/`headers`/xử lý lỗi tuỳ biến.
     * Đi qua [self] để giữ nguyên `@Retry`/`@CircuitBreaker` như [doPost] gốc.
     * @author <a href="https://github.com/henry0337">Muharux</a>
     */
    public fun <T : Any, B : Any> doPost(url: String, body: B, responseType: ParameterizedTypeReference<T>): T? =
        self.doPost(url, body, responseType, null, null, null, null)

    /**
     * Phiên bản rút gọn của [doPost] cho lệnh gọi từ **Java** kèm `params`/`headers` nhưng không cần xử lý lỗi
     * tuỳ biến. Đi qua [self] để giữ nguyên `@Retry`/`@CircuitBreaker` như [doPost] gốc.
     * @author <a href="https://github.com/henry0337">Muharux</a>
     */
    public fun <T : Any, B : Any> doPost(
        url: String,
        body: B,
        responseType: ParameterizedTypeReference<T>,
        params: @Nullable Map<String, Array<Any>>?,
        headers: @Nullable Map<String, @Nullable String?>?
    ): T? = self.doPost(url, body, responseType, params, headers, null, null)

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
     * @author <a href="https://github.com/henry0337">Muharux</a>
     */
    @KotlinVariant
    @JvmSynthetic
    public inline fun <reified T : Any> get(
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
     * @author <a href="https://github.com/henry0337">Muharux</a>
     */
    @KotlinVariant
    @JvmSynthetic
    public inline fun <reified T : Any, reified B : Any> post(
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

    @Suppress("unused")
    private fun <T : Any> retryFallback(ex: Throwable): T? =
        throw (ex as? HttpClientException ?: HttpClientException("Gọi HTTP thất bại sau khi đã thử lại (retry).", ex))

    @Suppress("unused")
    private fun <T : Any> circuitBreakerFallback(ex: Throwable): T? =
        throw (ex as? HttpClientException ?: HttpClientException("Circuit breaker đang mở, tạm dừng gọi HTTP.", ex))
}
