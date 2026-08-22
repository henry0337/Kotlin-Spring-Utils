package dev.myrlennia237.component.event

import org.springframework.boot.context.event.ApplicationPreparedEvent
import org.springframework.context.ApplicationEvent
import org.springframework.context.event.GenericApplicationListener
import org.springframework.core.ResolvableType

/**
 * Interface giúp can thiệp vào vòng đời của framework **Spring** ở giai đoạn **Prepared - Đã chuẩn bị xong**.
 *
 * Tức là khi:
 * - [SpringApplication][org.springframework.boot.SpringApplication] đang khởi động.
 * - [ApplicationContext][org.springframework.context.ApplicationContext] đã hoàn toàn được chuẩn bị xong **nhưng** chưa
 *   được làm mới.
 * - Tất cả các [Bean][org.springframework.context.annotation.Bean] đã được load và [Environment][org.springframework.core.env.Environment]
 *   đã có thể được sử dụng.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see ApplicationPreparedEvent
 * @see GenericApplicationListener
 */
public fun interface PreparedEventListener : GenericApplicationListener {
    public fun onApplicationPrepared(event: ApplicationPreparedEvent)

    override fun onApplicationEvent(event: ApplicationEvent) {
        onApplicationPrepared(event as ApplicationPreparedEvent)
    }

    override fun supportsEventType(eventType: ResolvableType): Boolean =
        ApplicationPreparedEvent::class.java.isAssignableFrom(eventType.toClass())
}