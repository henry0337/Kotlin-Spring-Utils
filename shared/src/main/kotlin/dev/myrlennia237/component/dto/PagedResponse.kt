package dev.myrlennia237.component.dto

import dev.myrlennia237.annotation.KotlinVariant
import org.springframework.data.domain.Page

/**
 * Phiên bản tối giản hơn của [Page].
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public data class PagedResponse<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
) {
    public companion object {
        /**
         * Phương thức khởi tạo nhanh một [PagedResponse] với các tham số lấy từ [Page].
         * @param source Tham số liên quan tới phân trang.
         */
        @JvmStatic
        public fun <T : Any> from(source: Page<T>): PagedResponse<T> = PagedResponse(
            content = source.content,
            page = source.number,
            size = source.size,
            totalElements = source.totalElements,
            totalPages = source.totalPages,
            hasNext = source.hasNext(),
            hasPrevious = source.hasPrevious(),
        )
    }
}

/**
 * Phương thức mở rộng dùng để khởi tạo nhanh một [PagedResponse] với các tham số lấy từ [Page].
 *
 * **Ghi chú**: Chỉ có thể được gọi bằng các API của Kotlin.
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
@JvmSynthetic
public fun <T : Any> Page<T>.toPagedResponse(): PagedResponse<T> = PagedResponse.from(this)