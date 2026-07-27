package dev.myrlennia237.template.service.java

import dev.myrlennia237.config.AsyncAuditorAware
import dev.myrlennia237.internal.java.service.Deletable
import dev.myrlennia237.internal.java.service.Insertable
import dev.myrlennia237.internal.java.service.Modifiable
import dev.myrlennia237.internal.java.service.ReadableWithID
import dev.myrlennia237.internal.java.service.Reversible
import dev.myrlennia237.template.service.BaseReactiveService
import dev.myrlennia237.helper.ReactorHelper
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
public abstract class AbstractCrudService<T : Any, in I1, in I2> : BaseReactiveService(),
    ReadableWithID<T>,
    Insertable<T, I1>,
    Modifiable<T, I2>,
    Deletable,
    Reversible {

    @set:Autowired
    protected lateinit var reactorHelper: ReactorHelper

    @set:Autowired
    protected lateinit var auditorAware: AsyncAuditorAware
}
