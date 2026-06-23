package dev.myrlennia237.internal.service.java

import reactor.core.publisher.Mono
import java.util.UUID

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
internal fun interface Deletable {
    /**
     * Thực hiện xóa dữ liệu dựa trên ID của chúng.
     * @param id ID của entity cần xóa
     */
    fun deleteById(id: UUID): Mono<Void>
}
