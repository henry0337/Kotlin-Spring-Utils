package dev.myrlennia237.template.service

import dev.myrlennia237.internal.service.Deletable
import dev.myrlennia237.internal.service.Insertable
import dev.myrlennia237.internal.service.Modifiable
import dev.myrlennia237.internal.service.ReadableWithID
import dev.myrlennia237.internal.service.Reversible

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
 * @see BaseService
 * @see Readable
 * @see ReadableWithID
 * @see Insertable
 * @see Modifiable
 * @see Deletable
 * @see Reversible
 * @sample dev.myrlennia237.sample.FooService
 */
public abstract class AbstractCrudService<T : Any, I1, I2> : BaseService(),
    ReadableWithID<T>,
    Insertable<T, I1>,
    Modifiable<T, I2>,
    Deletable,
    Reversible
