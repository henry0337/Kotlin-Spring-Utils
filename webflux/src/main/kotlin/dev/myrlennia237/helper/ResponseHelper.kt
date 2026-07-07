package dev.myrlennia237.helper

import dev.myrlennia237.config.ServerWebExchangeContextFilter
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public class ResponseHelper {
    /**
     * Trả về mã phản hồi `200 OK` với item được phát ra bởi [source].
     *
     * @param source [Mono] chứa dữ liệu cần trả về
     * @param T      Kiểu dữ liệu của response body
     */
    public fun <T : Any> ok(source: Mono<T>): Mono<ResponseEntity<T>> = source.map { ResponseEntity.ok(it) }

    /**
     * Trả về mã phản hồi `200 OK` không bao gồm với bất cứ item nào được phát ra cả.
     *
     * @param source [Mono] hoàn thành mà không phát ra dữ liệu
     * @return [Mono] phát ra [ResponseEntity] rỗng với status 200 OK
     */
    @Suppress("kotlin:S6508")
    public fun okEmpty(source: Mono<Void>): Mono<ResponseEntity<Void>> =
        source.then(Mono.just(ResponseEntity.ok().build()))

    /**
     * Trả về mã phản hồi `201 Created` với body trả về được lấy từ [source].
     *
     * @param source [Mono] chứa dữ liệu cần trả về
     * @param T      Kiểu dữ liệu của response body
     */
    public fun <T : Any> created(source: Mono<T>): Mono<ResponseEntity<T>> {
        return ServerWebExchangeContextFilter.getExchange()
            .flatMap { exchange ->
                source.map { body -> ResponseEntity.created(exchange.request.uri).body(body) }
            }
            .switchIfEmpty(source.map { body -> ResponseEntity.status(201).body(body) })
    }

    /**
     * Trả về mã phản hồi `204 No Content` không bao gồm với bất cứ item nào được phát ra cả.
     *
     * @param source [Mono] hoàn thành mà không phát ra dữ liệu
     * @return [Mono] phát ra [ResponseEntity] rỗng với status 204 No Content
     */
    @Suppress("kotlin:S6508")
    public fun noContent(source: Mono<Void>): Mono<ResponseEntity<Void>> =
        source.then(Mono.just(ResponseEntity.noContent().build()))

    /**
     * Trả về mã phản hồi `200 OK` nếu như [source] phát ra bất cứ giá trị hợp lệ ngoài [Mono.empty], không thì trả về
     * mã phản hồi `404 Not Found`.
     *
     * @param T      Kiểu dữ liệu của response body
     * @param source [Mono] chứa dữ liệu cần trả về
     * @return [Mono] phát ra [ResponseEntity] với status 200 OK hoặc 404 Not Found
     */
    public fun <T : Any> okOrNotFound(source: Mono<T>): Mono<ResponseEntity<T>> =
        source
            .map { ResponseEntity.ok(it) }
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
}
