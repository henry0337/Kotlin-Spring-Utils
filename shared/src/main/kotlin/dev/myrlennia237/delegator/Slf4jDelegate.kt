package dev.myrlennia237.delegator

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @see slf4j
 */
public class Slf4jDelegate : ReadOnlyProperty<Any, Logger> {

    @Volatile
    private var cached: Logger? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): Logger =
        cached ?: LoggerFactory.getLogger(thisRef.javaClass).also { cached = it }
}

/**
 * Tạo một property delegate cấp một [Logger] SLF4J, gắn theo lớp chứa property.
 *
 * Ví dụ: `private val logger by slf4j()`. Logger được khởi tạo lười và cache lại (an toàn luồng qua [Volatile]).
 *
 * @return delegate cung cấp [Logger] cho lớp sở hữu property.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public fun slf4j(): Slf4jDelegate = Slf4jDelegate()
