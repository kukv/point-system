package jp.kukv.point.domain.model.transaction

import jp.kukv.point._extensions.kotlinx.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.monthsUntil
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/** 取引日時 */
@JvmInline
@Serializable
value class TransactionTime(private val value: LocalDateTime) {
    fun is取引から1ヶ月以上経過(): Boolean {
        val currentDate = LocalDate.now()

        val period = value.date.monthsUntil(currentDate)
        return period > 0
    }

    operator fun invoke() = value

    override fun toString() = value.toString()
}
