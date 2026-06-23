package dev.myrlennia237.component

import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
class I18nService(private val messageSource: MessageSource) {
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
    fun translate(
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