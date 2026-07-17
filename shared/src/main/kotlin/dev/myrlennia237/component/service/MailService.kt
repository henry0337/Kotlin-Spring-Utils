package dev.myrlennia237.component.service

import jakarta.mail.internet.InternetAddress
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers.boundedElastic

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public class MailService(private val mailSender: JavaMailSender) {

    /**
     * Thực hiện gửi mail điện tử tới các [recipient] chỉ định.
     *
     * @param from          Người gửi mail
     * @param recipient     Đối tượng nhận mail, có thể nhiều hơn 1
     * @param subject       Tiêu đề của mail
     * @param body          Nội dung của mail
     * @param cc            Carbon Copy - Những người chỉ cần biết thông tin, không cần phản hồi, có thể được nhìn thấy
     *                      bởi các đối tượng nhận mail khác
     * @param bcc           Blind Carbon Copy - Những người chỉ cần biết thông tin, không cần phản hồi, không thể được
     *                      nhìn thấy bởi các đối tượng nhận mail khác
     * @param replyTo       Đối tượng nhận phản hồi
     * @param asHtml        Cho phép gửi nội dung mail dưới dạng HTML. Mặc định: `false`.
     */
    @JvmOverloads
    @Suppress("kotlin:S107")
    public fun sendMail(
        from: String,
        recipient: Array<out String>,
        subject: String,
        body: String,
        cc: Array<out String>? = null,
        bcc: Array<out String>? = null,
        replyTo: String? = null,
        asHtml: Boolean = false
    ) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setFrom(from)
        helper.setTo(recipient)
        helper.setSubject(subject)
        helper.setText(body, asHtml)

        cc?.let { helper.setCc(it) }
        bcc?.let { helper.setBcc(it) }
        replyTo?.let { helper.setReplyTo(InternetAddress(it)) }

        mailSender.send(message)
    }

    /**
     * Thực hiện gửi mail điện tử bất đồng bộ tới các [recipient] chỉ định.
     *
     * @param from          Người gửi mail
     * @param recipient     Đối tượng nhận mail, có thể nhiều hơn 1
     * @param subject       Tiêu đề của mail
     * @param body          Nội dung của mail
     * @param cc            Carbon Copy - Những người chỉ cần biết thông tin, không cần phản hồi, có thể được nhìn thấy
     *                      bởi các đối tượng nhận mail khác
     * @param bcc           Blind Carbon Copy - Những người chỉ cần biết thông tin, không cần phản hồi, không thể được
     *                      nhìn thấy bởi các đối tượng nhận mail khác
     * @param replyTo       Đối tượng nhận phản hồi
     * @param asHtml        Cho phép gửi nội dung mail dưới dạng HTML. Mặc định: `false`.
     */
    @JvmOverloads
    @Suppress("kotlin:S6508", "kotlin:S107")
    public fun sendMailAndAwait(
        from: String,
        recipient: Array<out String>,
        subject: String,
        body: String,
        cc: Array<out String>? = null,
        bcc: Array<out String>? = null,
        replyTo: String? = null,
        asHtml: Boolean = false
    ): Mono<Void> = Mono
        .fromCallable { sendMail(from, recipient, subject, body, cc, bcc, replyTo, asHtml) }
        .subscribeOn(boundedElastic())
        .then()
}