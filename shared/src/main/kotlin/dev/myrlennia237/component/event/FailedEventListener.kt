package dev.myrlennia237.component.event

import org.springframework.boot.context.event.ApplicationFailedEvent
import org.springframework.context.ApplicationEvent
import org.springframework.context.event.GenericApplicationListener
import org.springframework.core.ResolvableType

/**
 * Interface giúp can thiệp vào vòng đời của framework **Spring** ở giai đoạn **Failed - Khởi động thất bại**.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see ApplicationFailedEvent
 * @see GenericApplicationListener
 */
public fun interface FailedEventListener : GenericApplicationListener {
    public fun onApplicationFailed(event: ApplicationFailedEvent)

    override fun onApplicationEvent(event: ApplicationEvent) {
        onApplicationFailed(event as ApplicationFailedEvent)
    }

    override fun supportsEventType(eventType: ResolvableType): Boolean =
        ApplicationFailedEvent::class.java.isAssignableFrom(eventType.toClass())
}