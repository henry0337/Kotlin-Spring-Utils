package dev.myrlennia237.helper

import dev.myrlennia237.config.ServerWebExchangeContextFilter
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

/**
 * Helper hỗ trợ tạo URI cho môi trường **WebFlux**.
 * 
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
public object WebFluxUriBuilder {
    
    /**
     * Tạo một chuỗi URI đầy đủ từ toàn bộ các phần được chỉ định để cấu tạo nên một URL.
     *
     * @param scheme        Giao thức (`http`/`https`...)
     * @param host          Tên miền/địa chỉ của host
     * @param port          Cổng kết nối, bỏ trống (`null`) để dùng cổng mặc định của [scheme]
     * @param path          Đường dẫn (path) của URI
     * @param queryParams   Tập hợp các query param cần đính kèm, entry có giá trị `null` sẽ bị bỏ qua
     * @param fragment      Fragment (`#...`) của URI, bỏ trống (`null`) nếu không cần
     * @return Chuỗi URI hoàn chỉnh, đã được encode.
     */
    @JvmStatic
    @JvmOverloads
    public fun build(
        scheme: String,
        host: String,
        port: Int? = null,
        path: String,
        queryParams: Map<String, Any?> = emptyMap(),
        fragment: String? = null
    ): String = UriComponentsBuilder.newInstance()
        .scheme(scheme)
        .host(host)
        .path(path)
        .fragment(fragment)
        .apply {
            port?.let { this.port(it) }
            queryParams.forEach { (key, value) -> value?.let { queryParam(key, it) } }
        }
        .encode()
        .build()
        .toUriString()

    /**
     * Tạo một [UriComponentsBuilder] đã điền sẵn scheme/host/port/path/query đọc từ [request] được chỉ định.
     *
     * Tương đương [org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromRequest] bên Servlet,
     * nhưng nhận [request] tường minh thay vì đọc ngầm từ thread hiện tại — dùng trực tiếp trong handler/filter
     * đã có sẵn [request] mà không cần Reactor Context.
     *
     * @param request [ServerHttpRequest] cần đọc thông tin
     * @return [UriComponentsBuilder] đã điền sẵn thông tin từ [request]
     * @see UriComponentsBuilder.fromUri
     */
    public fun fromRequest(request: ServerHttpRequest): UriComponentsBuilder =
        UriComponentsBuilder.fromUri(request.uri)

    /**
     * Tạo một chuỗi URI dựa trên scheme/host/port cùng **context path** của request hiện tại (lấy qua Reactor
     * Context, xem [ServerWebExchangeContextFilter]), kèm theo [path] được chỉ định.
     *
     * @param path          Đường dẫn (path) của URI
     * @param queryParams   Tập hợp các query param cần đính kèm, entry có giá trị `null` sẽ bị bỏ qua
     * @param fragment      Fragment (`#...`) của URI, bỏ trống (`null`) nếu không cần
     * @return [Mono] phát ra chuỗi URI hoàn chỉnh, hoặc rỗng nếu không tìm thấy exchange hiện tại.
     */
    @JvmStatic
    @JvmOverloads
    public fun buildFromCurrentContextPath(
        path: String,
        queryParams: Map<String, Any?> = emptyMap(),
        fragment: String? = null
    ): Mono<String> = ServerWebExchangeContextFilter.getExchange().map { exchange ->
        fromRequest(exchange.request)
            .replacePath(exchange.request.path.contextPath().value())
            .path(path)
            .fragment(fragment)
            .apply {
                queryParams.forEach { (key, value) -> value?.let { queryParam(key, it) } }
            }
            .encode()
            .build()
            .toUriString()
    }

    /**
     * Tạo một chuỗi URI dựa trên toàn bộ **request hiện tại** (scheme/host/port/path), nối thêm [path] được chỉ
     * định vào cuối. Lấy exchange qua Reactor Context, xem [ServerWebExchangeContextFilter].
     *
     * @param path          Đường dẫn (path) cần nối thêm vào URI của request hiện tại
     * @param queryParams   Tập hợp các query param cần đính kèm, entry có giá trị `null` sẽ bị bỏ qua
     * @param fragment      Fragment (`#...`) của URI, bỏ trống (`null`) nếu không cần
     * @return [Mono] phát ra chuỗi URI hoàn chỉnh, hoặc rỗng nếu không tìm thấy exchange hiện tại.
     */
    @JvmStatic
    @JvmOverloads
    public fun buildFromCurrentRequestUri(
        path: String,
        queryParams: Map<String, Any?> = emptyMap(),
        fragment: String? = null
    ): Mono<String> = ServerWebExchangeContextFilter.getExchange().map { exchange ->
        fromRequest(exchange.request)
            .path(path)
            .fragment(fragment)
            .apply {
                queryParams.forEach { (key, value) -> value?.let { queryParam(key, it) } }
            }
            .encode()
            .build()
            .toUriString()
    }
}
