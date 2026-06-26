package dev.myrlennia237.component

import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

/**
 * Service dịch message từ [MessageSource] theo ngôn ngữ của request hiện tại.
 *
 * Locale được đọc từ [org.springframework.context.i18n.LocaleContextHolder], tích hợp liền mạch
 * với Spring MVC/WebFlux locale resolution. Nếu key không tồn tại trong resource bundle,
 * trả về chuỗi rỗng và ghi log cảnh báo thay vì ném exception.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see org.springframework.context.MessageSource
 * @see org.springframework.context.i18n.LocaleContextHolder
 */
public class I18nService(private val messageSource: MessageSource) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Dịch một message theo `key` từ `MessageSource` hiện có.
     *
     * Nếu `key` không tồn tại trong resource bundle, hàm sẽ log cảnh báo và trả về chuỗi rỗng.
     *
     * @param code Message code cần tra cứu
     * @param args Tham số thay thế cho message, nếu có
     * @param locale Locale dùng để resolve message; mặc định lấy từ `LocaleContextHolder`
     * @return Message đã dịch, hoặc chuỗi rỗng nếu không tìm thấy `key`
     * @author <a href="https://github.com/henry0337">Muharux</a>
     */
    @JvmOverloads
    public fun translate(
        code: String,
        args: Array<Any>? = null,
        locale: Locale = LocaleContextHolder.getLocale()
    ): String {
        return try {
            messageSource.getMessage(code, args, locale)
        } catch (e: NoSuchMessageException) {
            log.warn(e.localizedMessage)
            ""
        }
    }
}