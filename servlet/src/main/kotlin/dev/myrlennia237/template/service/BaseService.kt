package dev.myrlennia237.template.service

import dev.myrlennia237.component.service.I18nService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Một lớp wrapper [Service][org.springframework.stereotype.Service] chứa **dependency** có thể hữu ích trong khi
 * triển khai logic nghiệp vụ cho các lớp kế thừa lớp này.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see I18nService
 */
public abstract class BaseService {

    /**
     * **Localization Service** - Lớp quản lý các message được dịch cho các khu vực được hỗ trợ bởi hệ thống.
     */
    @set:Autowired(required = false)
    protected lateinit var i18nService: I18nService
}
