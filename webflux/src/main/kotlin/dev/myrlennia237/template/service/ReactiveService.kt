package dev.myrlennia237.template.service

import dev.myrlennia237.component.service.I18nService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Lớp nền cho tất cả service trong module **webflux**.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see I18nService
 */
public abstract class ReactiveService {
    @set:Autowired(required = false)
    protected lateinit var i18nService: I18nService
}
