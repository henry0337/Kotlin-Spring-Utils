package dev.myrlennia237.component.service

/**
 * Định dạng nội dung của email được gửi qua [MailService].
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public enum class MailBodyType {
    /** Nội dung thuần văn bản (plain text). */
    PLAIN,

    /** Nội dung dạng HTML. */
    HTML,
}
