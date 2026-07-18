package dev.myrlennia237

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.domain.JavaModifier
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import dev.myrlennia237.annotation.KotlinVariant
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Kiểm tra rằng tất cả API được đánh dấu [@KotlinVariant] đều được đánh dấu synthetic
 * (có cờ ACC_SYNTHETIC trong bytecode — được thiết lập bởi [@JvmSynthetic] hoặc [@file:JvmSynthetic]),
 * đảm bảo Java caller không thể truy cập chúng ở bytecode level.
 *
 * **Lưu ý kỹ thuật:** ArchUnit chỉ đọc annotation có retention `RUNTIME`. Vì [@JvmSynthetic] có retention
 * `BINARY` (tương đương `CLASS`), không thể dùng `isAnnotatedWith(JvmSynthetic::class.java)`.
 * Thay vào đó, kiểm tra cờ `ACC_SYNTHETIC` qua [JavaModifier.SYNTHETIC].
 *
 * Hai rule:
 * 1. **Direct** — method có `@KotlinVariant` trực tiếp phải là synthetic.
 * 2. **Cascade** — method cụ thể (non-abstract, public) trong class/file có `@KotlinVariant` cũng phải là synthetic.
 *
 * **Ngoại lệ của rule cascade** — các thành phần buộc phải public để tương tác với framework/lớp con,
 * không phải API Kotlin-only mà người dùng gọi trực tiếp, nên được loại khỏi kiểm tra:
 * - **Property accessor** (`get*`/`set*`/`is*`, `component#`, `copy`) — hợp đồng property cho Spring Data/serialization.
 * - **Entity class** (tên kết thúc `Entity`) — buộc expose accessor và domain helper cho framework và lớp kế thừa.
 * - **Interface default method** — helper trên các internal marker interface (`KConflictable`, `KRestorable`...),
 *   bản thân abstract method đã bị loại sẵn.
 */
class KotlinVariantEnforcementTest {

    private val classes: JavaClasses = ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("dev.myrlennia237")

    private fun isPropertyAccessor(name: String): Boolean =
        Regex("^(get|set|is)[A-Z_].*").matches(name) ||
        Regex("^component\\d+$").matches(name) ||
        name == "copy"

    @Test
    fun `@KotlinVariant concrete functions must be synthetic`() {
        val violations = classes
            .flatMap { it.methods }
            .filter { method ->
                method.isAnnotatedWith(KotlinVariant::class.java) &&
                !method.modifiers.contains(JavaModifier.ABSTRACT) &&
                !method.modifiers.contains(JavaModifier.SYNTHETIC)
            }

        assertThat(violations)
            .withFailMessage {
                "@KotlinVariant concrete functions chưa được đánh dấu synthetic (thiếu @JvmSynthetic):\n" +
                violations.joinToString("\n") { "  - ${it.fullName}" }
            }
            .isEmpty()
    }

    @Test
    fun `concrete public functions in @KotlinVariant classes must be synthetic`() {
        val violations = classes
            .asSequence()
            .filter { it.isAnnotatedWith(KotlinVariant::class.java) }
            .filter { !it.isInterface }                   // interface default method là helper, không phải API gọi trực tiếp
            .filter { !it.simpleName.endsWith("Entity") }  // entity buộc expose accessor + domain helper cho framework
            .flatMap { it.methods }
            .filter { method ->
                !method.modifiers.contains(JavaModifier.ABSTRACT) &&
                        method.modifiers.contains(JavaModifier.PUBLIC) &&
                        !method.name.contains("$") &&
                        !method.modifiers.contains(JavaModifier.SYNTHETIC) &&
                        !isPropertyAccessor(method.name)           // property accessor là hợp đồng framework, không phải API Kotlin-only
            }
            .toList()

        assertThat(violations)
            .withFailMessage {
                "Cascade vi phạm — method cụ thể trong @KotlinVariant class chưa synthetic:\n" +
                violations.joinToString("\n") { "  - ${it.fullName}" }
            }
            .isEmpty()
    }
}
