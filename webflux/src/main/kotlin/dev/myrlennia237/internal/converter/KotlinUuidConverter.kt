package dev.myrlennia237.internal.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

@WritingConverter
internal class KotlinUuidWritingConverter : Converter<Uuid, java.util.UUID> {
    override fun convert(source: Uuid): java.util.UUID = source.toJavaUuid()
}

@ReadingConverter
internal class KotlinUuidReadingConverter : Converter<java.util.UUID, Uuid> {
    override fun convert(source: java.util.UUID): Uuid = source.toKotlinUuid()
}
