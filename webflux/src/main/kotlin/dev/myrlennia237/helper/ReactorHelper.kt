package dev.myrlennia237.helper

import dev.myrlennia237.component.ImmutableList
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Helper cung cấp các toán tử được tùy chỉnh từ thư viện **Reactor**.
 *
 * Các phương thức được cung cấp trong này sẽ chỉ nên được tương tác **đơn lẻ** với từng toán tử khác nhau khác.
 * Không khuyến khích nối các toán tử này với các toán tử gốc của thư viện.
 * @see <a href="https://projectreactor.io/">Project Reactor</a>
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
public class ReactorHelper {
    /**
     * Tạo một [Mono] mới phát ra một [data] được chỉ định, dữ liệu đó sẽ được thu thập vào thời điểm khởi tạo.
     *
     * @param T         Kiểu dữ liệu của dữ liệu đầu vào
     * @param data      Dữ liệu sẽ được phát ra
     * @see Mono.just
     */
    public fun <T : Any> only(data: T): Mono<T> = Mono.just(data)

    /**
     * Tạo một [Mono] mới phát ra một [instance] được chỉ định nếu nó không `null`, ngược lại sẽ phát ra [Mono.empty].
     *
     * @param T                 Kiểu dữ liệu của dữ liệu đầu vào
     * @param instance          Dữ liệu sẽ được phát ra
     * @see Mono.justOrEmpty
     */
    public fun <T : Any> onlyOrEmpty(instance: T?): Mono<T> = Mono.justOrEmpty(instance)

    /**
     * Tạo một [Mono] mà không phát ra bất cứ dữ liệu nào cả.
     *
     * @param T Kiểu dữ liệu của dữ liệu đầu vào
     * @see Mono.empty
     */
    public fun <T : Any> emptyMono(): Mono<T> = Mono.empty()

    /**
     * Tạo một [Flux] mà không phát ra bất cứ dữ liệu nào cả.
     *
     * @param T Kiểu dữ liệu của dữ liệu đầu vào
     * @see Flux.empty
     */
    public fun <T : Any> emptyFlux(): Flux<T> = Flux.empty()

    /**
     * Ném một [exception] chỉ định với [message] mong muốn và khởi tạo [Mono] lập tức chấm dứt tiến trình hiện tại.
     */
    public fun <T : Any> errorMono(
        message: String,
        exception: Class<out Throwable> = RuntimeException::class.java
    ): Mono<T> {
        val throwable = runCatching { exception.getDeclaredConstructor(String::class.java).newInstance(message) }
            .getOrDefault(RuntimeException(message))
        return Mono.error(throwable)
    }

    /**
     * Ném một [exception] chỉ định với [message] mong muốn và khởi tạo [Flux] lập tức chấm dứt tiến trình hiện tại.
     *
     * @param message
     * @param exception
     */
    public fun <T : Any> errorFlux(
        message: String,
        exception: Class<out Throwable> = RuntimeException::class.java
    ): Flux<T> {
        val throwable = runCatching { exception.getDeclaredConstructor(String::class.java).newInstance(message) }
            .getOrDefault(RuntimeException(message))
        return Flux.error(throwable)
    }

    /**
     * Lấy ra giá trị được bọc trong một [Mono] được chỉ định.
     *
     * @param T         Kiểu dữ liệu được wrap trong [Mono]
     * @param publisher Đối tượng [Mono] cần lấy giá trị được wrap tương ứng
     * @return Giá trị được wrap bên trong nếu tồn tại, nếu như [Mono.empty] thì trả về `null`.
     */
    public fun <T : Any> wait(publisher: Mono<T>): T? = publisher.block()

    /**
     * Lấy ra giá trị [List] được bọc trong một [Flux] được chỉ định.
     *
     * @param T         Kiểu dữ liệu được wrap trong [Flux]
     * @param publisher Đối tượng [Flux] cần lấy giá trị được wrap tương ứng
     * @return Danh sách bất biến các giá trị được wrap bên trong nếu tồn tại, nếu như [Flux.empty] thì trả về danh sách rỗng.
     */
    public fun <T : Any> transformToList(publisher: Flux<T>): ImmutableList<T> =
        ImmutableList.copyFrom(publisher.collectList().block() ?: ImmutableList.empty())

    /**
     * Đánh dấu một [Mono] sẽ được bỏ qua giá trị trả về, kết quả thực tế sẽ được thay bằng `Mono<Void>`.
     * @see Mono.then
     */
    @Suppress("kotlin:S6508")
    public fun <T : Any> ignoreReturnValue(mono: Mono<T>): Mono<Void> = mono.then()
}
