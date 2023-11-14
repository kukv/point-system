package jp.kukv.point.extensions.kotlinx

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

val TimeZone.Companion.JST: TimeZone
    get() = of("Asia/Tokyo")

fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.JST): LocalDateTime = Clock.System.now().toLocalDateTime(timeZone)

fun LocalDate.Companion.now(timeZone: TimeZone = TimeZone.JST): LocalDate = LocalDateTime.now(timeZone).date

fun LocalDate.plusYears(value: Int): LocalDate = this.plus(value, DateTimeUnit.YEAR)
