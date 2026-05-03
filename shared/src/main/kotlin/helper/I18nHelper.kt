package helper

import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component
import java.util.Locale

@Component
class I18nHelper(private val messageSource: MessageSource) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * @param key
     * @param args
     * @param locale
     * @author <a href="https://github.com/henry0337">Myrlennia</a>
     */
    @JvmOverloads
    fun translate(
        key: String,
        args: Array<Any>? = null,
        locale: Locale = LocaleContextHolder.getLocale()
    ): String {
        return try {
            messageSource.getMessage(key, args, locale)
        } catch (e: NoSuchMessageException) {
            log.warn(e.localizedMessage)
            ""
        }
    }
}