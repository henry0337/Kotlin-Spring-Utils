package dev.myrlennia237.util

import dev.myrlennia237.config.ServerWebExchangeContextFilter
import dev.myrlennia237.dto.PagedResponse
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono

class ResponseHelper {
    /**
     * Bọc kết quả của [source] trong một [ResponseEntity] với HTTP status 200 OK.
     *
     * @param T      Kiểu dữ liệu của response body
     * @param source [Mono] chứa dữ liệu cần trả về
     * @return [Mono] phát ra [ResponseEntity] với status 200 OK
     */
    fun <T : Any> awaitOk(source: Mono<T>): Mono<ResponseEntity<T>> = source.map { ResponseEntity.ok(it) }

    /**
     * Bọc kết quả của [source] trong một [ResponseEntity] rỗng với HTTP status 200 OK.
     *
     * @param source [Mono] hoàn thành mà không phát ra dữ liệu
     * @return [Mono] phát ra [ResponseEntity] rỗng với status 200 OK
     */
    fun awaitOkEmpty(source: Mono<Void>): Mono<ResponseEntity<Void>> =
        source.then(Mono.just(ResponseEntity.ok().build()))

    /**
     * Bọc kết quả của [source] trong một [ResponseEntity] với HTTP status 201 Created.
     * URI của resource mới sẽ được lấy từ request context nếu có, ngược lại fallback về status 201 đơn giản.
     *
     * @param T      Kiểu dữ liệu của response body
     * @param source [Mono] chứa dữ liệu cần trả về
     * @return [Mono] phát ra [ResponseEntity] với status 201 Created
     */
    fun <T : Any> awaitCreated(source: Mono<T>): Mono<ResponseEntity<T>> {
        return ServerWebExchangeContextFilter.getExchange()
            .flatMap { exchange ->
                source.map { body -> ResponseEntity.created(exchange.request.uri).body(body) }
            }
            .switchIfEmpty(source.map { body -> ResponseEntity.status(201).body(body) })
    }

    /**
     * Bọc kết quả của [source] trong một [ResponseEntity] rỗng với HTTP status 204 No Content.
     *
     * @param source [Mono] hoàn thành mà không phát ra dữ liệu
     * @return [Mono] phát ra [ResponseEntity] rỗng với status 204 No Content
     */
    fun awaitNoContent(source: Mono<Void>): Mono<ResponseEntity<Void>> =
        source.then(Mono.just(ResponseEntity.noContent().build()))

    /**
     * Bọc kết quả của [source] trong một [ResponseEntity] với HTTP status 200 OK,
     * hoặc 404 Not Found nếu [source] không phát ra dữ liệu.
     *
     * @param T      Kiểu dữ liệu của response body
     * @param source [Mono] chứa dữ liệu cần trả về
     * @return [Mono] phát ra [ResponseEntity] với status 200 OK hoặc 404 Not Found
     */
    fun <T : Any> awaitOrNotFound(source: Mono<T>): Mono<ResponseEntity<T>> {
        return source.map<ResponseEntity<T>> { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    /**
     * Bọc kết quả của [source] trong một [ResponseEntity] 200 OK sau khi chuyển đổi [Page] thành [PagedResponse].
     *
     * @param T      Kiểu dữ liệu của từng phần tử trong trang
     * @param source [Mono] chứa [Page] cần chuyển đổi
     * @return [Mono] phát ra [ResponseEntity] với status 200 OK bọc [PagedResponse]
     */
    fun <T : Any> awaitPaged(source: Mono<Page<T>>): Mono<ResponseEntity<PagedResponse<T>>> =
        source.map { page -> ResponseEntity.ok(PagedResponse.from(page)) }
}
