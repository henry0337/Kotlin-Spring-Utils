package dev.myrlennia237.template.service

import dev.myrlennia237.component.I18nService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Indexed

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@Indexed
abstract class BaseReactiveService {
    @set:Autowired
    protected lateinit var i18nService: I18nService
}
