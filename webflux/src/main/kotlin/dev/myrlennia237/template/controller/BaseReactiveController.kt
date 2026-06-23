package dev.myrlennia237.template.controller

import dev.myrlennia237.component.I18nService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Indexed

/**
 * Lớp base cho tất cả controller có sẵn trong dự án.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@Indexed
abstract class BaseReactiveController {
    @set:Autowired(required = false)
    protected lateinit var i18nService: I18nService
}
