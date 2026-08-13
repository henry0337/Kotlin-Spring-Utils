package dev.myrlennia237.component.helper

import org.springframework.core.env.Environment

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
public class EnvironmentHelper(private val env: Environment) {
    private companion object {
        const val DEV = "dev"
        const val STG = "staging"
        const val PRD = "prod"
    }
    
    /**
     * Kiểm tra xem **cấu hình môi trường đang chạy** hiện tại có phải là môi trường **phát triển (development)** không.
     * 
     * @return Giá trị `true`/`false` tương ứng.
     */
    public fun isDevelopment(): Boolean = env.matchesProfiles(DEV)
    
    /**
     * Kiểm tra xem **cấu hình môi trường đang chạy** hiện tại có phải là môi trường **staging** không.
     * 
     * @return Giá trị `true`/`false` tương ứng.
     */
    public fun isStaging(): Boolean = env.matchesProfiles(STG)
    
    /**
     * Kiểm tra xem **cấu hình môi trường đang chạy** hiện tại có phải là môi trường **thực tế (production)** không.
     * 
     * @return Giá trị `true`/`false` tương ứng.
     */
    public fun isProduction(): Boolean = env.matchesProfiles(PRD)
}