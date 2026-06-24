package dev.myrlennia237.annotation

/**
 * Tiện ích bảo vệ runtime cho các API được đánh dấu [KotlinVariant].
 *
 * Gọi [check] ở đầu mỗi hàm cần giới hạn với Kotlin để ném [IllegalCallerException]
 * ngay tại runtime nếu caller là Java class.
 *
 * @see KotlinVariant
 */
object KotlinVariantGuard {

    private const val LIB_PACKAGE = "dev.myrlennia237"

    /**
     * Ném [IllegalCallerException] nếu caller trực tiếp không phải Kotlin code.
     *
     * Kotlin class được nhận diện qua annotation [Metadata] mà Kotlin compiler
     * tự động thêm vào mọi class được biên dịch từ `.kt` hoặc `.kts`.
     */
    fun check() {
        val externalFrame = Thread.currentThread()
            .stackTrace
            .drop(1) // bỏ qua getStackTrace()
            .firstOrNull { isExternalFrame(it) }
            ?: return

        val callerClass = runCatching { Class.forName(externalFrame.className) }
            .getOrNull()
            ?: return

        if (callerClass.getAnnotation(Metadata::class.java) == null) {
            throw IllegalCallerException(
                "'${externalFrame.className}' là Java class — " +
                "@KotlinVariant API chỉ được gọi từ Kotlin code."
            )
        }
    }

    /**
     * Xác định xem [frame] có đến từ code bên ngoài thư viện hay không.
     *
     * Stack frame thuộc package `dev.myrlennia237`, JDK (`java.*`, `jdk.*`, `sun.*`)
     * hoặc Kotlin runtime (`kotlin.*`, `kotlinx.*`) đều bị bỏ qua.
     *
     * @param frame Stack frame cần kiểm tra
     * @return `true` nếu frame đến từ code ngoài thư viện và ngoài runtime
     */
    private fun isExternalFrame(frame: StackTraceElement): Boolean {
        val cls = frame.className
        return !cls.startsWith(LIB_PACKAGE) &&
            !cls.startsWith("java.") &&
            !cls.startsWith("javax.") &&
            !cls.startsWith("kotlin.") &&
            !cls.startsWith("kotlinx.") &&
            !cls.startsWith("sun.") &&
            !cls.startsWith("jdk.")
    }
}