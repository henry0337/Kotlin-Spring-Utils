package dev.myrlennia237.template.service.java

import dev.myrlennia237.config.AsyncAuditorAware
import dev.myrlennia237.internal.java.service.Deletable
import dev.myrlennia237.internal.java.service.Insertable
import dev.myrlennia237.internal.java.service.Modifiable
import dev.myrlennia237.internal.java.service.ReadableWithID
import dev.myrlennia237.internal.java.service.Reversible
import dev.myrlennia237.template.service.ReactiveService
import dev.myrlennia237.helper.ReactorHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.PropertyMapper
import org.springframework.stereotype.Service

/**
 * Một lớp định nghĩa các contract cho các phương thức đại diện cho logic nghiệp vụ cơ bản liên quan tới CRUD.
 *
 * Việc mà phía consumer cần làm sẽ chỉ đơn giản là **triển khai logic tương tác database** (hoặc logic nghiệp vụ khác,
 * nếu có kế thừa các interface khác) cho từng phương thức bên trong.
 *
 * @param T  Dữ liệu đầu ra - thường là **Aggregate Root**, **Projection** hoặc **DTO** mong muốn
 * @param I1 **DTO** dùng làm nơi nhận dữ liệu để **thêm mới (Create)** vào cơ sở dữ liệu
 * @param I2 **DTO** dùng làm nơi nhận dữ liệu để **cập nhật (Update)** vào bản ghi có sẵn trong cơ sở dữ liệu
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @see ReactiveService
 * @see ReadableWithID
 * @see Insertable
 * @see Modifiable
 * @see Deletable
 * @see Reversible
 */
public abstract class AbstractCrudService<T : Any, in I1, in I2> : ReactiveService(),
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
