package internal.service.java

import reactor.core.publisher.Mono

/**
 * Java variant cho contract vô hiệu hóa hoặc khôi phục trạng thái entity theo định danh.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal fun interface Reversible<in ID> {
    /**
     * Vô hiệu hóa entity theo định danh.
     *
     * @param id Định danh của entity cần vô hiệu hóa
     * @return `Mono` hoàn tất khi thao tác kết thúc
     */
    fun disable(id: ID): Mono<Void>
}
