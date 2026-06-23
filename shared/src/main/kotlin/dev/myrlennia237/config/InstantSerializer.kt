package dev.myrlennia237.config

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import dev.myrlennia237.JavaInstant

object InstantSerializer : KSerializer<JavaInstant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: JavaInstant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): JavaInstant {
        return JavaInstant.parse(decoder.decodeString())
    }
}
