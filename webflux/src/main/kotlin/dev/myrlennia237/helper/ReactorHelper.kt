package dev.myrlennia237.helper

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Helper cung cấp các toán tử được tùy chỉnh từ thư viện **Reactor**.
 *
 * Các phương thức được cung cấp trong này sẽ chỉ nên được tương tác **đơn lẻ** với từng toán tử khác nhau khác.
 * Không khuyến khích nối các toán tử này với các toán tử gốc của thư viện.
 * @see <a href="https://projectreactor.io/">Project Reactor</a>
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
public class ReactorHelper {
    /**
     * Tạo một [Mono] mới phát ra một [data] được chỉ định, dữ liệu đó sẽ được thu thập vào thời điểm khởi tạo.
     *
     * @param T         Kiểu dữ liệu của dữ liệu đầu vào
     * @param data      Dữ liệu sẽ được phát ra
     * @see Mono.just
     */
    public fun <T : Any> emit(data: T): Mono<T> = Mono.just(data)

    /**
     * Tạo một [Mono] mới phát ra một [instance] được chỉ định nếu nó không `null`, nếu `null` thì sẽ phát ra [Mono.empty].
     *
     * @param T                 Kiểu dữ liệu của dữ liệu đầu vào
     * @param instance          Dữ liệu sẽ được phát ra
     * @see Mono.justOrEmpty
     */
    public fun <T : Any> emitThisOrEmpty(instance: T?): Mono<T> = Mono.justOrEmpty(instance)

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
     * Tạo ra một [Mono] sẽ phát ra một [error] được chỉ định ngay lập tức sau khi được subscribe tới.
     *
     * @param error     Exception cần được ném ra
     * @see Mono.error
     */
    public fun <T : Any> error(error: Exception): Mono<T> = Mono.error(error)
    
    // /**
    //  * Tạo ra một [Mono] sẽ phát ra một [RuntimeException] bọc [message] và [exception] ngay lập tức sau khi được
    //  * subscribe tới.
    //  *
    //  * @param message   Mô tả lỗi
    //  * @param exception Nguyên nhân gốc, được giữ qua [Throwable.cause]
    //  * @see Mono.error
    //  */
    // @JvmOverloads
    // public fun <T : Any> error(
    //     message: String = "Có lỗi xảy ra!",
    //     cause: Throwable = Exception()
    // ): Mono<T> = Mono.error(RuntimeException(message, cause))
    
    /**
     * Trì hoãn việc tạo ra một [Mono] phát ra [error] cho đến khi có một [Subscriber][org.reactivestreams.Subscriber]
     * thực hiện subscribe vào nó.
     * 
     * @param error Exception cần được ném ra khi được subscribe tới
     * @return [Mono] phát ra lỗi cần được ném ra.
     * @see Mono.defer
     * @see Mono.error
     */
    public fun <T : Any> deferError(error: Exception): Mono<T> = Mono.defer { Mono.error(error) }

    /**
     * Lấy ra giá trị được bọc trong một [Mono] bằng cách **chặn luồng vô thời hạn** cho đến khi nhận được
     * tín hiệu kế tiếp từ chính [Mono] đó.
     *
     * @param T     Kiểu dữ liệu được wrap trong [Mono]
     * @param mono  Đối tượng [Mono] cần lấy giá trị được wrap tương ứng
     * @return Giá trị được wrap bên trong nếu tồn tại, nếu như [Mono.empty] thì trả về `null`.
     */
    public fun <T : Any> awaitMonoComplete(mono: Mono<T>): T? = mono.block()

    /**
     * Chuyển đổi sang một [Mono] chỉ phát ra tín hiệu hoàn thành hoặc lỗi dựa trên kết quả thực tế.
     * 
     * `Mono<Void>` sẽ được trả về theo quy chuẩn của Project Reactor.
     * @see Mono.then
     */
    @Suppress("kotlin:S6508")
    public fun <T : Any> emitCompleteSignal(mono: Mono<T>): Mono<Void> = mono.then()
}
