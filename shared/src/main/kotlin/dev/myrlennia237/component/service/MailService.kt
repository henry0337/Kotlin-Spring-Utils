package dev.myrlennia237.component.service

import jakarta.mail.internet.InternetAddress
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers.boundedElastic

/**
 * Dịch vụ gửi email, bọc [JavaMailSender] với hai biến thể: đồng bộ ([sendMail]) và bất đồng bộ
 * ([sendMailAndAwait], chạy trên [Schedulers.boundedElastic][reactor.core.scheduler.Schedulers.boundedElastic]).
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
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
     * @param bodyType      Định dạng nội dung mail (plain text hoặc HTML). Mặc định: [MailBodyType.PLAIN].
     * @throws IllegalArgumentException nếu [from]/[subject] để trống hoặc [recipient] không có địa chỉ nào.
     * @throws org.springframework.mail.MailException nếu quá trình gửi mail thất bại.
     * @throws jakarta.mail.MessagingException nếu dựng nội dung mail (địa chỉ, tiêu đề...) thất bại.
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
        bodyType: MailBodyType = MailBodyType.PLAIN
    ) {
        require(from.isNotBlank()) { "\"from\" không được để trống." }
        require(recipient.isNotEmpty()) { "\"recipient\" phải có ít nhất một địa chỉ nhận." }
        require(subject.isNotBlank()) { "\"subject\" không được để trống." }

        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setFrom(from)
        helper.setTo(recipient)
        helper.setSubject(subject)
        helper.setText(body, bodyType == MailBodyType.HTML)

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
     * @param bodyType      Định dạng nội dung mail (plain text hoặc HTML). Mặc định: [MailBodyType.PLAIN].
     * @return [Mono] hoàn tất khi mail đã gửi xong. Thao tác gửi chạy trên
     *   [Schedulers.boundedElastic][reactor.core.scheduler.Schedulers.boundedElastic] (an toàn cho tác vụ blocking).
     *   Lỗi (xem [sendMail]) được phát qua tín hiệu `onError` thay vì ném đồng bộ.
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
        bodyType: MailBodyType = MailBodyType.PLAIN
    ): Mono<Void> = Mono
        .fromCallable { sendMail(from, recipient, subject, body, cc, bcc, replyTo, bodyType) }
        .subscribeOn(boundedElastic())
        .then()
}