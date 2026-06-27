package dev.myrlennia237.template.service

import dev.myrlennia237.component.service.I18nService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Indexed

/**
 * Lớp nền cho tất cả service trong module **servlet** (Spring MVC).
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see I18nService
 */
@Indexed
public abstract class BaseService {
    @set:Autowired(required = false)
    protected lateinit var i18nService: I18nService
}
