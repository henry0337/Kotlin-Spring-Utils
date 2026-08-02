package dev.myrlennia237.sample

import dev.myrlennia237.config.BlockingAuditorAware
import dev.myrlennia237.service.HttpClient
import dev.myrlennia237.service.RedisService
import org.springframework.stereotype.Component

@Component
internal class FooComponent1(private val redisService: RedisService) // <--- Inject vào là xong!

@Component
internal class FooComponent2(private val httpClient: HttpClient) // <--- Inject vào là xong!

@Component
internal class FooComponent3(private val auditorAware: BlockingAuditorAware) // <--- Inject vào là xong!