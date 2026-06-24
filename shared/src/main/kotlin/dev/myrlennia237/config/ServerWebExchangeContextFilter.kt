package dev.myrlennia237.config

import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.Context

/**
 * [WebFilter] lưu [ServerWebExchange] hiện tại vào Reactor [reactor.util.context.Context],
 * cho phép truy xuất exchange từ bất kỳ đâu trong chuỗi reactive mà không cần truyền tường minh.
 *
 * Được auto-configure bởi thư viện. Để lấy exchange trong một [Mono] bất kỳ:
 * ```kotlin
 * ServerWebExchangeContextFilter.getExchange()
 *     .flatMap { exchange -> Mono.just(exchange.request.uri) }
 * ```
 *
 * @see getExchange
 */
class ServerWebExchangeContextFilter : WebFilter {
    /**
     * Đặt [exchange] vào Reactor Context rồi tiếp tục chuỗi filter.
     *
     * @param exchange Exchange của request hiện tại
     * @param chain    Chuỗi filter tiếp theo
     * @return [Mono] hoàn thành khi toàn bộ chuỗi filter xử lý xong
     */
    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> {
        return chain.filter(exchange).contextWrite(Context.of(ServerWebExchange::class.java, exchange))
    }

    companion object {
        /**
         * Lấy [ServerWebExchange] từ Reactor Context của chuỗi hiện tại.
         *
         * Trả về [Mono.empty][reactor.core.publisher.Mono.empty] nếu [ServerWebExchangeContextFilter]
         * chưa được đăng ký hoặc context không chứa exchange.
         *
         * @return [Mono] phát ra exchange hiện tại, hoặc rỗng nếu không tìm thấy
         */
        @JvmStatic
        fun getExchange(): Mono<ServerWebExchange> {
            return Mono.deferContextual {
                Mono.justOrEmpty(it.getOrEmpty(ServerWebExchange::class.java))
            }
        }
    }
}