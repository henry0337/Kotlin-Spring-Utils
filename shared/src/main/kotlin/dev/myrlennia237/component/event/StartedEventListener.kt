package dev.myrlennia237.component.event

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationEvent
import org.springframework.context.event.GenericApplicationListener
import org.springframework.core.ResolvableType

/**
 * Interface giúp can thiệp vào vòng đời của framework **Spring** ở giai đoạn **Started - Đã khởi động**, tức là khi
 * [ApplicationContext][org.springframework.context.ApplicationContext] đã được làm mới nhưng trước khi bất cứ
 * [ApplicationRunner][org.springframework.boot.ApplicationRunner] hoặc [CommandLineRunner][org.springframework.boot.CommandLineRunner]
 * nào được gọi tới.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see ApplicationStartedEvent
 * @see GenericApplicationListener
 */
public fun interface StartedEventListener : GenericApplicationListener {
    public fun onApplicationStarted(event: ApplicationStartedEvent)

    override fun onApplicationEvent(event: ApplicationEvent) {
        onApplicationStarted(event as ApplicationStartedEvent)
    }

    override fun supportsEventType(eventType: ResolvableType): Boolean =
        ApplicationStartedEvent::class.java.isAssignableFrom(eventType.toClass())
}