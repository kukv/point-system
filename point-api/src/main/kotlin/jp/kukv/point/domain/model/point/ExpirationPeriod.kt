package jp.kukv.point.domain.model.point

import jp.kukv.point._extensions.kotlinx.datetime.now
import jp.kukv.point._extensions.kotlinx.datetime.plusYears
import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmInline

/** 有効期限 */
@JvmInline
value class ExpirationPeriod(private val value: LocalDate) {
    fun isExpired(): Boolean {
        val currentDate = LocalDate.now()
        return value < currentDate
    }

    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object {
        fun prototype(): ExpirationPeriod {
            val date = LocalDate.now()
            return ExpirationPeriod(date.plusYears(1))
        }
    }
}
