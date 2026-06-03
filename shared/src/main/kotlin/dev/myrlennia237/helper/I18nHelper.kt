package dev.myrlennia237.helper

import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

/**
 * Helper hỗ trợ tra cứu và dịch message từ [MessageSource] của Spring.
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
class I18nHelper(private val messageSource: MessageSource) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Dịch một message theo `key` từ `MessageSource` hiện có.
     *
     * Nếu `key` không tồn tại trong resource bundle, hàm sẽ log cảnh báo và trả về chuỗi rỗng.
     *
     * @param key Message code cần tra cứu
     * @param args Tham số thay thế cho message, nếu có
     * @param locale Locale dùng để resolve message; mặc định lấy từ `LocaleContextHolder`
     * @return Message đã dịch, hoặc chuỗi rỗng nếu không tìm thấy `key`
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
