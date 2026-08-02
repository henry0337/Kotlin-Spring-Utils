package dev.myrlennia237.component.service

import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

/**
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see org.springframework.context.MessageSource
 * @see org.springframework.context.i18n.LocaleContextHolder
 */
public class I18nService(private val messageSource: MessageSource) {
    private val logger = LoggerFactory.getLogger(I18nService::class.java)

    /**
     * Dịch một message theo [code] từ [MessageSource] hiện có.
     *
     * Nếu [code] không tồn tại trong resource bundle, hàm sẽ log cảnh báo và trả về chuỗi rỗng.
     *
     * @param code Message code cần tra cứu
     * @param args Tham số thay thế cho các placeholder trong message, nếu có
     * @param locale Locale dùng để resolve message
     * @return Message đã dịch, hoặc chuỗi rỗng nếu không tìm thấy [code].
     * @author <a href="https://github.com/henry0337">Myrlennia</a>
     */
    @JvmOverloads
    public fun translate(
        code: String,
        args: Array<Any>? = null,
        locale: Locale = LocaleContextHolder.getLocale()
    ): String {
        return try {
            messageSource.getMessage(code, args, locale)
        } catch (_: NoSuchMessageException) {
            logger.warn("Không tìm thấy message với code '{}' cho locale '{}'.", code, locale)
            ""
        }
    }
}