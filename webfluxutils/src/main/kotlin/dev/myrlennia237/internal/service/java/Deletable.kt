package dev.myrlennia237.internal.service.java

import reactor.core.publisher.Mono

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal fun interface Deletable<in ID> {
    /**
     * Thực hiện xóa dữ liệu dựa trên ID của chúng.
     * @param id ID của entity cần xóa
     */
    fun deleteById(id: ID): Mono<Void>
}
