package dev.myrlennia237.utils

import java.time.Duration
import java.time.LocalDateTime
import java.time.Month

/**
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
public object TimeUtils {
    /**
     * Khởi tạo một instance của [Duration] từ số ngày, giờ, phút, giây.
     *
     * @param days    số ngày
     * @param hours   số giờ
     * @param minutes số phút
     * @param seconds số giây
     * @return [Duration] tương ứng
     * @throws IllegalArgumentException nếu bất kỳ thành phần nào là số âm.
     */
    @JvmStatic
    public fun ofDuration(days: Long, hours: Long, minutes: Long, seconds: Long): Duration {
        require(days >= 0 && hours >= 0 && minutes >= 0 && seconds >= 0) {
            "Các thành phần thời lượng phải không âm: days=$days, hours=$hours, minutes=$minutes, seconds=$seconds"
        }
        return Duration.ofDays(days)
            .plusHours(hours)
            .plusMinutes(minutes)
            .plusSeconds(seconds)
    }

    /**
     * Khởi tạo một instance của [LocalDateTime] từ năm, tháng, ngày, giờ, phút, giây.
     *
     * @param year   năm
     * @param month  tháng
     * @param day    ngày
     * @param hour   giờ
     * @param minute phút
     * @param second giây
     * @return [LocalDateTime] tương ứng
     * @throws java.time.DateTimeException nếu bất kỳ tham số nào nằm ngoài khoảng hợp lệ (ví dụ `month` không
     *   thuộc `1..12`, hoặc `day` không hợp lệ với tháng/năm tương ứng).
     */
    @JvmStatic
    public fun ofLocalDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): LocalDateTime {
        return LocalDateTime.of(year, Month.of(month), day, hour, minute, second)
    }
}
