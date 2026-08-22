package dev.myrlennia237.component.event

import org.springframework.boot.context.event.ApplicationContextInitializedEvent
import org.springframework.context.ApplicationEvent
import org.springframework.context.event.GenericApplicationListener
import org.springframework.core.ResolvableType

/**
 * Interface giúp can thiệp vào vòng đời của framework **Spring** ở giai đoạn **Context Initialized - Khởi tạo Context**.
 *
 * Tức là khi:
 * - [SpringApplication][org.springframework.boot.SpringApplication] đang khởi động.
 * - [ApplicationContext][org.springframework.context.ApplicationContext] được chuẩn bị.
 * - [ApplicationContextInitializer][org.springframework.context.ApplicationContextInitializer] đã được gọi **nhưng**
 * trước khi bất cứ [Bean][org.springframework.context.annotation.Bean] nào được load.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see ApplicationContextInitializedEvent
 * @see GenericApplicationListener
 */
public fun interface ContextInitializedEventListener : GenericApplicationListener {
    public fun onContextInitialized(event: ApplicationContextInitializedEvent)

    override fun onApplicationEvent(event: ApplicationEvent) {
        onContextInitialized(event as ApplicationContextInitializedEvent)
    }

    override fun supportsEventType(eventType: ResolvableType): Boolean =
        ApplicationContextInitializedEvent::class.java.isAssignableFrom(eventType.toClass())
}