package internal.service.java

import reactor.core.publisher.Mono

/**
 * Java variant cho contract cập nhật một entity theo định danh.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
internal fun interface Modifiable<T : Any, in ID, in I> {
    /**
     * Cập nhật một entity theo định danh.
     *
     * @param id Định danh của entity cần cập nhật
     * @param body Dữ liệu cập nhật
     * @return `Mono` bọc entity sau khi cập nhật
     */
    fun update(id: ID, body: I): Mono<T>
}
