package dev.myrlennia237.component.event

import org.springframework.boot.context.event.ApplicationStartingEvent
import org.springframework.context.ApplicationEvent
import org.springframework.context.event.GenericApplicationListener
import org.springframework.core.ResolvableType

/**
 * Interface giúp can thiệp vào vòng đời của framework **Spring** ở giai đoạn **Starting - Đang khởi chạy**.
 *
 * Tức là:
 * - Sớm nhất có thể sau khi [SpringApplication][org.springframework.boot.SpringApplication] đã khởi động (tức trước khi
 * [Environment][org.springframework.core.env.Environment] hoặc [ApplicationContext][org.springframework.context.ApplicationContext]
 * khả dụng).
 * - Sau khi các [ApplicationListener][org.springframework.context.ApplicationListener] đã được thêm vào hệ thống.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see ApplicationStartingEvent
 * @see GenericApplicationListener
 */
public fun interface StartingEventListener : GenericApplicationListener {
    public fun onApplicationStarting(event: ApplicationStartingEvent)

    override fun onApplicationEvent(event: ApplicationEvent) {
        onApplicationStarting(event as ApplicationStartingEvent)
    }

    override fun supportsEventType(eventType: ResolvableType): Boolean =
        ApplicationStartingEvent::class.java.isAssignableFrom(eventType.toClass())
}