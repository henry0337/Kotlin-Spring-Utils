package dev.myrlennia237.config

import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.Context

class ServerWebExchangeContextFilter : WebFilter {
    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> {
        return chain.filter(exchange).contextWrite(Context.of(ServerWebExchange::class.java, exchange))
    }

    companion object {
        @JvmStatic
        fun getExchange(): Mono<ServerWebExchange> {
            return Mono.deferContextual {
                Mono.justOrEmpty(it.getOrEmpty(ServerWebExchange::class.java))
            }
        }
    }
}