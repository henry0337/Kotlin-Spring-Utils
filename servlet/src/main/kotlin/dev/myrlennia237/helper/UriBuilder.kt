package dev.myrlennia237.helper

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.util.UriComponentsBuilder

public class UriBuilder {
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
     * Tạo một chuỗi URI dựa trên scheme/host/port của **request hiện tại**, kèm theo [path] được chỉ định.
     *
     * @param path          Đường dẫn (path) của URI
     * @param queryParams   Tập hợp các query param cần đính kèm, entry có giá trị `null` sẽ bị bỏ qua
     * @param fragment      Fragment (`#...`) của URI, bỏ trống (`null`) nếu không cần
     * @return Chuỗi URI hoàn chỉnh, đã được encode.
     */
    @JvmOverloads
    public fun buildFromCurrentContextPath(
        path: String,
        queryParams: Map<String, Any?> = emptyMap(),
        fragment: String? = null
    ): String = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path(path)
        .fragment(fragment)
        .apply {
            queryParams.forEach { (key, value) -> value?.let { queryParam(key, it) } }
        }
        .encode()
        .build()
        .toUriString()

    /**
     * Tạo một chuỗi URI dựa trên scheme/host/port cùng **servlet mapping** của request hiện tại, kèm theo [path]
     * được chỉ định.
     *
     * @param path          Đường dẫn (path) của URI
     * @param queryParams   Tập hợp các query param cần đính kèm, entry có giá trị `null` sẽ bị bỏ qua
     * @param fragment      Fragment (`#...`) của URI, bỏ trống (`null`) nếu không cần
     * @return Chuỗi URI hoàn chỉnh, đã được encode.
     */
    @JvmOverloads
    public fun buildFromCurrentServletMapping(
        path: String,
        queryParams: Map<String, Any?> = emptyMap(),
        fragment: String? = null
    ): String = ServletUriComponentsBuilder.fromCurrentServletMapping()
        .path(path)
        .fragment(fragment)
        .apply {
            queryParams.forEach { (key, value) -> value?.let { queryParam(key, it) } }
        }
        .encode()
        .build()
        .toUriString()

    /**
     * Tạo một chuỗi URI dựa trên toàn bộ **request URI hiện tại** (scheme/host/port/path), nối thêm [path] được
     * chỉ định vào cuối.
     *
     * @param path          Đường dẫn (path) cần nối thêm vào URI của request hiện tại
     * @param queryParams   Tập hợp các query param cần đính kèm, entry có giá trị `null` sẽ bị bỏ qua
     * @param fragment      Fragment (`#...`) của URI, bỏ trống (`null`) nếu không cần
     * @return Chuỗi URI hoàn chỉnh, đã được encode.
     */
    @JvmOverloads
    public fun buildFromCurrentRequestUri(
        path: String,
        queryParams: Map<String, Any?> = emptyMap(),
        fragment: String? = null
    ): String = ServletUriComponentsBuilder.fromCurrentRequestUri()
        .path(path)
        .fragment(fragment)
        .apply {
            queryParams.forEach { (key, value) -> value?.let { queryParam(key, it) } }
        }
        .encode()
        .build()
        .toUriString()
}