package dev.myrlennia237.util

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import java.util.function.Predicate
import java.util.function.Supplier

class ReactorHelper {
    /**
     * Tạo một [Mono] mới phát ra một [data] được chỉ định, dữ liệu đó sẽ được thu thập vào thời điểm khởi tạo.
     *
     * @param T    Kiểu dữ liệu của dữ liệu đầu vào
     * @param data Dữ liệu sẽ được phát ra
     * @see Mono.just
     */
    fun <T : Any> single(data: T): Mono<T> = Mono.just(data)

    /**
     * Tạo một [Mono] mới phát ra một [instance] được chỉ định nếu nó không `null`, ngược lại sẽ phát ra [Mono.empty].
     *
     * @param T        Kiểu dữ liệu của dữ liệu đầu vào
     * @param instance Dữ liệu sẽ được phát ra
     * @see Mono.justOrEmpty
     */
    fun <T : Any> singleOrEmpty(instance: T?): Mono<T> = Mono.justOrEmpty(instance)

    /**
     * Tạo một [Mono] mà không phát ra bất cứ dữ liệu nào cả.
     *
     * @param T Kiểu dữ liệu của dữ liệu đầu vào
     * @see Mono.empty
     */
    fun <T : Any> emptyMono(): Mono<T> = Mono.empty()

    /**
     * Tạo một [Flux] mà không phát ra bất cứ dữ liệu nào cả.
     *
     * @param T Kiểu dữ liệu của dữ liệu đầu vào
     * @see Flux.empty
     */
    fun <T : Any> emptyFlux(): Flux<T> = Flux.empty()

    /**
     * Lấy ra giá trị được bọc trong một [Mono] được chỉ định.
     *
     * @param T         Kiểu dữ liệu được wrap trong [Mono]
     * @param publisher Đối tượng [Mono] cần lấy giá trị được wrap tương ứng
     * @return Giá trị được wrap bên trong nếu tồn tại, nếu như [Mono.empty] thì trả về `null`.
     */
    fun <T : Any> awaitMono(publisher: Mono<T>): T? = publisher.block()

    /**
     * Lấy ra giá trị [List] được bọc trong một [Flux] được chỉ định.
     *
     * @param T         Kiểu dữ liệu được wrap trong [Flux]
     * @param publisher Đối tượng [Flux] cần lấy giá trị được wrap tương ứng
     * @return Danh sách giá trị được wrap bên trong nếu tồn tại, nếu như [Flux.empty] thì trả về `null`.
     */
    fun <T : Any> awaitFluxToList(publisher: Flux<T>): List<T>? = publisher.collectList().block()

    /**
     * Ném ra một exception thông qua [errorSupplier] nếu [source] không chứa dữ liệu.
     *
     * @param T             Kiểu dữ liệu được bọc trong [Mono]
     * @param source        [Mono] cần kiểm tra
     * @param errorSupplier Hàm cung cấp exception khi [source] rỗng
     * @return [Mono] chứa dữ liệu ban đầu hoặc lỗi tương ứng
     */
    fun <T : Any> errorIfEmpty(source: Mono<T>, errorSupplier: Supplier<out Throwable>): Mono<T> =
        source.switchIfEmpty(Mono.error(errorSupplier))

    /**
     * Kiểm tra tính hợp lệ của dữ liệu trong [source] bằng [predicate], ném lỗi từ [errorSupplier] nếu không thỏa mãn.
     *
     * @param T             Kiểu dữ liệu được bọc trong [Mono]
     * @param source        [Mono] chứa dữ liệu cần kiểm tra
     * @param predicate     Điều kiện kiểm tra dữ liệu
     * @param errorSupplier Hàm cung cấp exception khi điều kiện không thỏa mãn
     * @return [Mono] chứa dữ liệu nếu hợp lệ, ngược lại chứa lỗi
     */
    fun <T : Any> ensure(
        source: Mono<T>,
        predicate: Predicate<T>,
        errorSupplier: Supplier<out Throwable>
    ): Mono<T> = source.flatMap { value ->
        if (predicate.test(value)) Mono.just(value) else Mono.error(errorSupplier)
    }

    /**
     * Thực hiện phân nhánh dựa trên [condition].
     *
     * @param T                 Kiểu dữ liệu trả về
     * @param condition         Điều kiện logic
     * @param thenSupplier      Hàm cung cấp luồng dữ liệu khi [condition] là `true`
     * @param otherwiseSupplier Hàm cung cấp luồng dữ liệu khi [condition] là `false`
     * @return [Mono] kết quả từ luồng được chọn
     */
    fun <T : Any> `when`(
        condition: Boolean,
        thenSupplier: Supplier<out Mono<T>>,
        otherwiseSupplier: Supplier<out Mono<T>>
    ): Mono<T> = if (condition) Mono.defer(thenSupplier) else Mono.defer(otherwiseSupplier)

    /**
     * Kết hợp hai [Mono] thành một [Tuple2].
     *
     * @param A          Kiểu dữ liệu của publisher thứ nhất
     * @param B          Kiểu dữ liệu của publisher thứ hai
     * @param publisher1 [Mono] thứ nhất
     * @param publisher2 [Mono] thứ hai
     * @return [Mono] phát ra [Tuple2] chứa kết quả của cả hai publisher
     */
    fun <A : Any, B : Any> group(publisher1: Mono<A>, publisher2: Mono<B>): Mono<Tuple2<A, B>> =
        publisher1.zipWith(publisher2)
}
