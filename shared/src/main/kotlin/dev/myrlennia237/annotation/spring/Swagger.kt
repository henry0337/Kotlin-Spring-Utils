package dev.myrlennia237.annotation.spring

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.core.annotation.AliasFor
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Đánh dấu một lớp được đánh dấu là [@RestController][RestController] sẽ được coi là một module/chức năng cụ thể được
 * thể hiện trong **đặc tả OpenAPI**.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@RestController
@RequestMapping
@Tag(name = "")
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
public annotation class ApiController(
    /**
     * URI path đại diện cho module/chức năng đó.
     */
    @get:AliasFor(annotation = RequestMapping::class, attribute = "path")
    vararg val path: String,

    /**
     * Tên của module/chức năng sẽ được hiển thị trong đặc tả OpenAPI.
     */
    @get:AliasFor(annotation = Tag::class, attribute = "name")
    val moduleName: String = "",

    /**
     * Mô tả về module/chức năng.
     */
    @get:AliasFor(annotation = Tag::class, attribute = "description")
    val description: String = ""
)

/**
 * Đánh dấu **phương thức đại diện** cho một HTTP endpoint cụ thể trong [@RestController][RestController] sẽ được tích hợp thêm
 * khả năng định nghĩa **đặc tả OpenAPI** cho một endpoint cụ thể.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Operation
@RequestMapping
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
public annotation class ApiMethod(
    /**
     * Phương thức HTTP mà endpoint đại diện sẽ xử lý.
     *
     * Giá trị hợp lệ: [RequestMethod.GET], [RequestMethod.POST], [RequestMethod.PUT], [RequestMethod.PATCH],
     * [RequestMethod.DELETE].
     */
    @get:AliasFor(annotation = RequestMapping::class, attribute = "method")
    val method: Array<RequestMethod>,

    /**
     * Domain path của endpoint dựa trên domain path gốc hiện tại của controller.
     */
    @get:AliasFor(annotation = RequestMapping::class, attribute = "path")
    val path: Array<String> = [],

    /**
     * Tóm tắt ngắn gọn về endpoint.
     *
     * Nên điền giá trị ít hơn 120 ký tự.
     */
    @get:AliasFor(annotation = Operation::class, attribute = "summary")
    val endpointSummary: String = "",

    /**
     * Mô tả chi tiết về endpoint.
     */
    @get:AliasFor(annotation = Operation::class, attribute = "description")
    val endpointDescription: String = "",

    /**
     * Danh sách các [MediaType][org.springframework.http.MediaType] mà phương thức này **nhận vào**.
     */
    @get:AliasFor(annotation = RequestMapping::class, attribute = "consumes")
    val consumeType: Array<String> = [],

    /**
     * Danh sách các [MediaType][org.springframework.http.MediaType] mà phương thức này **cung cấp ra**.
     */
    @get:AliasFor(annotation = RequestMapping::class, attribute = "produces")
    val produceType: Array<String> = [],

    /**
     * Phiên bản API tương ứng cho phương thức hiện tại.
     */
    @get:AliasFor(annotation = RequestMapping::class, attribute = "version")
    val version: String = "",

    /**
     * Đánh dấu endpoint là đã lỗi thời trong đặc tả OpenAPI.
     *
     * Giá trị mặc định: `false`
     */
    @get:AliasFor(annotation = Operation::class, attribute = "deprecated")
    val markAsDeprecated: Boolean = false,

    /**
     * Đánh dấu endpoint là sẽ bị ẩn khỏi đặc tả OpenAPI.
     * 
     * Giá trị mặc định: `false`
     */
    @get:AliasFor(annotation = Operation::class, attribute = "hidden")
    val markAsHidden: Boolean = false,
)

/**
 * Đánh dấu một **tham số** của phương thức đại diện cho một HTTP endpoint là một phần của [Operation] trong
 * **đặc tả OpenAPI**.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Parameter
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
public annotation class ApiParameter(
    /**
     * Tên của tham số hiển thị trong đặc tả OpenAPI.
     *
     * Giá trị mặc định sẽ dựa theo tên tham số được truyền vào phương thức.
     */
    @get:AliasFor(annotation = Parameter::class, attribute = "name")
    val name: String = "",

    /**
     * Vị trí sử dụng của tham số này trong request.
     *
     * Giá trị mặc định: [ParameterIn.DEFAULT] (Khuyên dùng).
     *
     * Giá trị thỏa mãn: [ParameterIn.PATH], [ParameterIn.QUERY], [ParameterIn.HEADER], [ParameterIn.COOKIE].
     */
    @get:AliasFor(annotation = Parameter::class, attribute = "in")
    val type: ParameterIn = ParameterIn.DEFAULT,

    /**
     * Mô tả mục đích sử dụng của tham số.
     */
    @get:AliasFor(annotation = Parameter::class, attribute = "description")
    val description: String = "",

    /**
     * Đánh dấu tham số này là bắt buộc.
     *
     * Giá trị mặc định: `true`.
     */
    @get:AliasFor(annotation = Parameter::class, attribute = "required")
    val required: Boolean = true,

    /**
     * Cho phép truyền giá trị rỗng. Nếu `false`, tham số không có giá trị sẽ được coi là `null`.
     *
     * Giá trị mặc định: `false`.
     */
    @get:AliasFor(annotation = Parameter::class, attribute = "allowEmptyValue")
    val allowEmpty: Boolean = false,

    /**
     * Đánh dấu tham số này là đã không còn được khuyên sử dụng.
     *
     * Giá trị mặc định: `false`.
     */
    @get:AliasFor(annotation = Parameter::class, attribute = "deprecated")
    val markAsDeprecated: Boolean = false,

    /**
     * Đánh dấu tham số này sẽ bị ẩn khỏi đặc tả OpenAPI.
     *
     * Giá trị mặc định: `false`.
     */
    @get:AliasFor(annotation = Parameter::class, attribute = "hidden")
    val markAsHidden: Boolean = false,

    /**
     * Giá trị giả định của tham số, hiển thị trong mô tả của Swagger UI.
     */
    @get:AliasFor(annotation = Parameter::class, attribute = "example")
    val usageExample: String = "",
)

/**
 * Đánh dấu một **tham số** của phương thức đại diện cho một HTTP endpoint cụ thể là một request body của một [Operation]
 * được thể hiện trong **đặc tả OpenAPI**.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@RequestBody
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
public annotation class ApiRequestBody(
    /**
     * Mô tả về mục đích sử dụng của request body.
     */
    @get:AliasFor(annotation = RequestBody::class, attribute = "description")
    val description: String = "",

    /**
     * Đánh dấu request body được truyền vào này là bắt buộc.
     *
     * Giá trị mặc định: `true`.
     */
    @get:AliasFor(annotation = RequestBody::class, attribute = "required")
    val required: Boolean = true,
)

/**
 * Đánh dấu một lớp và các thuộc tính của nó khi được sử dụng như request body/response body (lớp) là một phần của một
 * [Operation] được thể hiện trong **đặc tả OpenAPI**.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Schema
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
public annotation class ApiSchema(
    /**
     * Tên hiển thị của lớp/thuộc tính trên đặc tả OpenAPI.
     */
    @get:AliasFor(annotation = Schema::class, attribute = "name")
    val name: String = "",

    /**
     * Mô tả mục đích sử dụng của lớp/thuộc tính này.
     */
    @get:AliasFor(annotation = Schema::class, attribute = "title")
    val title: String = "",

    /**
     * Mô tả chi tiết hơn về lớp/thuộc tính này.
     */
    @get:AliasFor(annotation = Schema::class, attribute = "description")
    val description: String = "",

    /**
     * Đánh dấu một thuộc tính là bắt buộc khi được truyền vào request.
     *
     * Giá trị mặc định: [Schema.RequiredMode.REQUIRED].
     *
     * Giá trị thỏa mãn: [Schema.RequiredMode.REQUIRED], [Schema.RequiredMode.NOT_REQUIRED].
     */
    @get:AliasFor(annotation = Schema::class, attribute = "requiredMode")
    val requiredMode: Schema.RequiredMode = Schema.RequiredMode.REQUIRED,

    /**
     * Danh sách các thuộc tính bắt buộc phải được hiển thị trên đặc tả OpenAPI.
     */
    @get:AliasFor(annotation = Schema::class, attribute = "requiredProperties")
    val requiredPropsList: Array<String> = [],

    /**
     * Đánh dấu một thuộc tính có thể coi `null` là giá trị hợp lệ khi truyền vào.
     *
     * Giá trị mặc định: `false`.
     */
    @get:AliasFor(annotation = Schema::class, attribute = "nullable")
    val nullable: Boolean = false,

    /**
     * Xác định hướng di chuyển của field này trong API — xuất hiện trong request body, response body, hay cả hai.
     *
     * | Giá trị | Trong request | Trong response | Dùng khi |
     * |---|---|---|---|
     * | [Schema.AccessMode.READ_WRITE] | ✓ | ✓ | Field thông thường (`name`, `email`, `status`) |
     * | [Schema.AccessMode.READ_ONLY]  | ✗ | ✓ | Server tự sinh (`id`, `createdAt`, `createdBy`) |
     * | [Schema.AccessMode.WRITE_ONLY] | ✓ | ✗ | Field nhạy cảm không được trả về (`password`, `pin`) |
     *
     * Giá trị mặc định: [Schema.AccessMode.READ_WRITE].
     */
    @get:AliasFor(annotation = Schema::class, attribute = "accessMode")
    val accessMode: Schema.AccessMode = Schema.AccessMode.READ_WRITE,

    /**
     * Đánh dấu lớp/thuộc tính này đã bị coi là lỗi thời.
     *
     * Giá trị mặc định: `false`.
     */
    @get:AliasFor(annotation = Schema::class, attribute = "deprecated")
    val markAsDeprecated: Boolean = false,

    /**
     * Đánh dấu lớp/thuộc tính sẽ bị ẩn khỏi đặc tả OpenAPI
     *
     * Giá trị mặc định: `false`.
     */
    @get:AliasFor(annotation = Schema::class, attribute = "hidden")
    val markAsHidden: Boolean = false
)
