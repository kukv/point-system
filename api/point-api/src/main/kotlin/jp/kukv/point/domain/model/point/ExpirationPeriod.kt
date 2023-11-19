package jp.kukv.point.domain.model.point

import jp.kukv.point._extensions.kotlinx.now
import jp.kukv.point._extensions.kotlinx.plusYears
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/** 有効期限 */
@JvmInline
@Serializable
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
