package dev.myrlennia237.template.service.kotlin

import dev.myrlennia237.annotation.KotlinVariant
import dev.myrlennia237.config.KAsyncAuditorAware
import dev.myrlennia237.internal.kotlin.service.KDeletable
import dev.myrlennia237.internal.kotlin.service.KInsertable
import dev.myrlennia237.internal.kotlin.service.KModifiable
import dev.myrlennia237.internal.kotlin.service.KReadableWithID
import dev.myrlennia237.internal.kotlin.service.KReversible
import dev.myrlennia237.template.service.BaseReactiveService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Chỉ định một lớp được đánh dấu là [Service] là một lớp nghiệp vụ chuyên xử lý các tác vụ liên quan tới CRUD.
 *
 * @param T  **Entity** hoặc **Output DTO** mong muốn
 * @param I1 **Input DTO** dùng để tạo mới bản ghi
 * @param I2 **Input DTO** dùng để cập nhật dữ liệu bản ghi
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@KotlinVariant
public abstract class CoroutineCrudService<T : Any, in I1, in I2> : BaseReactiveService(),
    KReadableWithID<T>,
    KInsertable<T, I1>,
    KModifiable<T, I2>,
    KDeletable,
    KReversible {

    @set:Autowired
    protected lateinit var auditorAware: KAsyncAuditorAware
}
