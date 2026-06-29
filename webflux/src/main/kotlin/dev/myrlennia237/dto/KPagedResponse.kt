@file:KotlinVariant
package dev.myrlennia237.dto

import dev.myrlennia237.annotation.KotlinVariant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.springframework.data.domain.Page

public data class KPagedResponse<T>(
    val content: ImmutableList<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
)

/**
 * Phương thức mở rộng dùng để khởi tạo nhanh một [KPagedResponse] với các tham số lấy từ [Page].
 *
 * **Ghi chú**: Chỉ có thể được gọi bằng các API của Kotlin.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@JvmSynthetic
public fun <T : Any> Page<T>.toPagedResponse(): KPagedResponse<T> = KPagedResponse(
    content = this.content.toImmutableList(),
    page = this.number,
    size = this.size,
    totalElements = this.totalElements,
    totalPages = this.totalPages,
    hasNext = this.hasNext(),
    hasPrevious = this.hasPrevious()
)