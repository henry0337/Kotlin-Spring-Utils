package dev.myrlennia237.template.controller

import dev.myrlennia237.component.I18nService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Indexed

/**
 * Lớp nền cho tất cả REST controller trong module **webflux**.
 *
 * Cung cấp [i18nService][dev.myrlennia237.component.I18nService] được inject tự động — các lớp kế thừa
 * có thể gọi `i18nService.translate(...)` để trả về thông báo lỗi đã được dịch mà không cần
 * khai báo thêm dependency.
 * Inject dùng `required = false` để ứng dụng vẫn khởi động được nếu
 * [I18nService][dev.myrlennia237.component.I18nService] chưa được cấu hình.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see dev.myrlennia237.component.I18nService
 */
@Indexed
abstract class ReactiveRestController {
    @set:Autowired(required = false)
    protected lateinit var i18nService: I18nService
}
