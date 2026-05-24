package internal.service.java

import reactor.core.publisher.Mono

/**
 * Java variant cho contract xóa một entity theo định danh.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal fun interface Deletable<in ID> {
    /**
     * Xóa entity theo định danh.
     *
     * @param id Định danh của entity cần xóa
     * @return `Mono` hoàn tất khi thao tác kết thúc
     */
    fun deleteById(id: ID): Mono<Void>
}
