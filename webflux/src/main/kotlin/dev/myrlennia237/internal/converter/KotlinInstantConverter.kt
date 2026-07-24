package dev.myrlennia237.internal.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant

@WritingConverter
internal class KotlinInstantWritingConverter : Converter<Instant, java.time.Instant> {
    override fun convert(source: Instant): java.time.Instant = source.toJavaInstant()
}

@ReadingConverter
internal class KotlinInstantReadingConverter : Converter<java.time.Instant, Instant> {
    override fun convert(source: java.time.Instant): Instant = source.toKotlinInstant()
}
