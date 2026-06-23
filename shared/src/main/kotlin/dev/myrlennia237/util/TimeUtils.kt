package dev.myrlennia237.util

import java.time.Duration
import java.time.LocalDateTime
import java.time.Month

object TimeUtils {
    /**
     * Tạo [Duration] từ số ngày, giờ, phút, giây.
     *
     * @param days    số ngày
     * @param hours   số giờ
     * @param minutes số phút
     * @param seconds số giây
     * @return [Duration] tương ứng
     */
    @JvmStatic
    fun ofDuration(days: Long, hours: Long, minutes: Long, seconds: Long): Duration {
        return Duration.ofDays(days)
            .plusHours(hours)
            .plusMinutes(minutes)
            .plusSeconds(seconds)
    }

    /**
     * Tạo [LocalDateTime] từ năm, tháng, ngày, giờ, phút, giây.
     *
     * @param year   năm
     * @param month  tháng (1–12)
     * @param day    ngày
     * @param hour   giờ
     * @param minute phút
     * @param second giây
     * @return [LocalDateTime] tương ứng
     */
    @JvmStatic
    fun ofLocalDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): LocalDateTime {
        return LocalDateTime.of(year, Month.of(month), day, hour, minute, second)
    }
}
