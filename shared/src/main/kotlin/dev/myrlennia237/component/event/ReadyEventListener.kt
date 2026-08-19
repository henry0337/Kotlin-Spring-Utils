package dev.myrlennia237.component.event

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationEvent
import org.springframework.context.event.GenericApplicationListener
import org.springframework.core.ResolvableType

/**
 * Interface giúp can thiệp vào vòng đời của framework **Spring** ở giai đoạn **Ready - Có thể sử dụng**.
 *
 * Đây là sự kiện sẽ khởi chạy muộn nhất có thể để thông báo tới hệ thống rằng nó (hệ thống) đã có thể tiếp nhận các yêu cầu bên
 * ngoài.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see ApplicationReadyEvent
 * @see GenericApplicationListener
 */
public fun interface ReadyEventListener : GenericApplicationListener {
    /**
     * Thêm logic can thiệp vào giai đoạn này.
     *
     * @param event Sự kiện phản hồi tới
     */
    public fun onApplicationReady(event: ApplicationReadyEvent)

    override fun onApplicationEvent(event: ApplicationEvent) {
        onApplicationReady(event as ApplicationReadyEvent)
    }

    override fun supportsEventType(eventType: ResolvableType): Boolean =
        ApplicationReadyEvent::class.java.isAssignableFrom(eventType.toClass())
}