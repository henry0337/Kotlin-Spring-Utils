package dev.myrlennia237.annotation.spring

import org.springframework.core.annotation.AliasFor
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * Đánh dấu một phương thức thuộc về một [transaction][Transactional] chỉ tối ưu cho việc đọc dữ liệu.
 * 
 * Đây là phiên bản tối giản cho annotation [Transactional] sẵn có của **Spring Boot**.
 * 
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Transactional(readOnly = true, rollbackFor = [Exception::class])
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
public annotation class EffectiveReadOnlyTransactional(
    @get:AliasFor(annotation = Transactional::class, attribute = "label")
    val name: String = "",
    
    @get:AliasFor(annotation = Transactional::class, attribute = "propagation")
    val propagation: Propagation = Propagation.REQUIRED,
    
    @get:AliasFor(annotation = Transactional::class, attribute = "timeout")
    val timeout: Int = TransactionDefinition.TIMEOUT_DEFAULT
)