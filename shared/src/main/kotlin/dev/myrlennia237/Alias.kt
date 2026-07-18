package dev.myrlennia237

/**
 * Alias cho [java.time.Instant].
 */
public typealias JavaInstant = java.time.Instant

/**
 * Alias cho [java.io.Serializable].
 */
public typealias JavaSerializable = java.io.Serializable

/**
 * Alias cho [java.time.Duration].
 */
public typealias JavaDuration = java.time.Duration

/**
 * Alias cho kiểu hàm một tham số trả về [Boolean] — tương đương [Predicate][java.util.function.Predicate].
 */
public typealias Predicate<T> = (T) -> Boolean

/**
 * Alias cho kiểu hàm biến đổi [T] thành [R] — tương đương [Function][java.util.function.Function].
 */
public typealias Function<T, R> = (T) -> R
