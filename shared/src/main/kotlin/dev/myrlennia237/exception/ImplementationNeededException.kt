package dev.myrlennia237.exception

public class ImplementationNeededException(
    message: String = "Phương thức này cần được triển khai!",
    throwable: Throwable? = null
) : RuntimeException(message, throwable)