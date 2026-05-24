package annotation

import java.lang.annotation.Inherited

/**
 * **[[Marker Annotation]]**
 *
 * Đánh dấu một API được cung cấp bởi thư viện này sẽ chỉ khả dụng cho ngôn ngữ **Kotlin** khi được sử dụng.
 *
 * Nếu như được sử dụng bởi một ngôn ngữ **JVM** khác tương thích với **Spring Framework** (Java, Groovy)
 * sẽ gây ra lỗi không tương thích mong muốn.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @since 0.1.0
 * @see <a href="https://dev.java/">Java</a>
 * @see <a href="https://kotlinlang.org/">Kotlin</a>
 * @see <a href="https://groovy-lang.org/">Groovy</a>
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class KotlinVariant
