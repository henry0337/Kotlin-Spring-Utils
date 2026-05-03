package utils

import java.io.Serializable
import java.time.Duration
import java.time.LocalDateTime

/**
 * Bí danh đại diện cho kiểu [LocalDateTime].
 */
typealias JavaLocalDateTime = LocalDateTime

/**
 * Bí danh đại diện cho giao diện [Serializable].
 */
typealias JavaSerializable = Serializable

/**
 * Bí danh đại diện cho kiểu [Duration].
 */
typealias JavaDuration = Duration

/**
 * Bí danh đại diện cho giao diện hàm [java.util.function.Predicate].
 */
typealias Predicate<T> = (T) -> Boolean

/**
 * Bí danh đại diện cho giao diện hàm [java.util.function.Function].
 */
typealias Function<T, R> = (T) -> R