package dev.myrlennia237.config

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import dev.myrlennia237.JavaInstant

/**
 * [KSerializer] tùy chỉnh cho [JavaInstant], tuần tự hóa thành chuỗi ISO-8601.
 *
 * Dùng với annotation `@Serializable(with = InstantSerializer::class)` trên trường kiểu
 * [java.time.Instant] khi JSON response cần serialization rõ ràng:
 * ```kotlin
 * @Serializable
 * data class Event(
 *     @Serializable(with = InstantSerializer::class)
 *     val createdAt: Instant
 * )
 * ```
 */
object InstantSerializer : KSerializer<JavaInstant> {
    /**
     * Mô tả schema cho [JavaInstant] — ánh xạ về nguyên thủy [String].
     */
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    /**
     * Ghi [value] ra chuỗi ISO-8601 thông qua [encoder].
     *
     * @param encoder Encoder được framework truyền vào
     * @param value   Giá trị [JavaInstant] cần ghi
     */
    override fun serialize(encoder: Encoder, value: JavaInstant) {
        encoder.encodeString(value.toString())
    }

    /**
     * Phân tích chuỗi ISO-8601 từ [decoder] thành [JavaInstant].
     *
     * @param decoder Decoder được framework truyền vào
     * @return [JavaInstant] đã được phân tích từ chuỗi ISO-8601
     * @throws java.time.format.DateTimeParseException nếu chuỗi không đúng định dạng ISO-8601
     */
    override fun deserialize(decoder: Decoder): JavaInstant {
        return JavaInstant.parse(decoder.decodeString())
    }
}
