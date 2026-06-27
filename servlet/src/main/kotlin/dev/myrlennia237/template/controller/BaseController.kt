package dev.myrlennia237.template.controller

import dev.myrlennia237.component.service.I18nService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Indexed

/**
 * Lớp nền cho tất cả REST controller trong module **servlet** (Spring MVC).
 *
 * Cung cấp quyền truy cập vào [I18nService][I18nService] nếu bean này
 * có trong context — lớp kế thừa có thể gọi `i18nService.translate(...)` để trả thông báo lỗi
 * đã được dịch mà không cần khai báo thêm dependency.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see I18nService
 */
@Indexed
public abstract class BaseController {
    @set:Autowired(required = false)
    protected lateinit var i18nService: I18nService
}
