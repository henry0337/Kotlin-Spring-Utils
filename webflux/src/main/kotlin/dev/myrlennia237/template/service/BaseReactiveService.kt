package dev.myrlennia237.template.service

import dev.myrlennia237.component.I18nService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Indexed

/**
 * Lớp nền cho tất cả service trong module **webflux**.
 *
 * Cung cấp quyền truy cập vào [I18nService][dev.myrlennia237.component.I18nService] nếu bean này
 * có trong context — lớp kế thừa có thể gọi `i18nService.translate(...)` mà không cần khai báo
 * thêm dependency.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see dev.myrlennia237.component.I18nService
 */
@Indexed
abstract class BaseReactiveService {
    @set:Autowired(required = false)
    protected lateinit var i18nService: I18nService
}
