package dev.myrlennia237.template.controller

import dev.myrlennia237.component.service.I18nService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Lớp cha cho tất cả REST controller trong module **webflux**.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see I18nService
 */
public abstract class ReactiveController {

    /**
     * **Localization Service** - Lớp quản lý các message được dịch cho các khu vực được hỗ trợ bởi hệ thống.
     */
    @set:Autowired(required = false)
    protected lateinit var i18nService: I18nService
}
