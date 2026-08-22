package dev.myrlennia237.component.event

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationEvent
import org.springframework.context.event.GenericApplicationListener
import org.springframework.core.ResolvableType

/**
 * Interface giúp can thiệp vào vòng đời của framework **Spring** ở giai đoạn **Environment Prepared - Tinh chỉnh môi trường**.
 *
 * Tức là khi:
 * - [SpringApplication][org.springframework.boot.SpringApplication] đang khởi động.
 * - [Environment][org.springframework.core.env.Environment] đã có thể được theo dõi và tinh chỉnh.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see ApplicationEnvironmentPreparedEvent
 * @see GenericApplicationListener
 */
public fun interface EnvironmentPreparedEventListener : GenericApplicationListener {
    public fun onEnvironmentPrepared(event: ApplicationEnvironmentPreparedEvent)

    override fun onApplicationEvent(event: ApplicationEvent) {
        onEnvironmentPrepared(event as ApplicationEnvironmentPreparedEvent)
    }

    override fun supportsEventType(eventType: ResolvableType): Boolean =
        ApplicationEnvironmentPreparedEvent::class.java.isAssignableFrom(eventType.toClass())
}