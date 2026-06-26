package dev.myrlennia237

import java.io.Serializable
import java.time.Duration
import java.time.Instant

/**
 * Alias cho [Instant] — tường minh hóa nguồn gốc Java của kiểu thời gian UTC.
 */
public typealias JavaInstant = Instant

/**
 * Alias cho [Serializable] — dùng để khai báo entity có thể serialize mà không cần
 * import trực tiếp `java.io.Serializable`.
 */
public typealias JavaSerializable = Serializable

/**
 * Alias cho [Duration] — tường minh hóa nguồn gốc Java của kiểu thời lượng.
 *
 * Dùng khi cần phân biệt với [kotlin.time.Duration] trong cùng một file.
 */
public typealias JavaDuration = Duration

/**
 * Alias cho kiểu hàm một tham số trả về [Boolean] — tương đương [java.util.function.Predicate].
 */
public typealias Predicate<T> = (T) -> Boolean

/**
 * Alias cho kiểu hàm biến đổi [T] thành [R] — tương đương [java.util.function.Function].
 */
public typealias Function<T, R> = (T) -> R
