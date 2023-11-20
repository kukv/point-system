package jp.kukv.point_expiration_execution.domain.model.point

import jp.kukv.point_expiration_execution._extensions.kotlinx.now
import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmInline

/** 有効期限 */
@JvmInline
value class ExpirationPeriod(private val value: LocalDate) {
    fun isExpired(): Boolean {
        val currentDate = LocalDate.now()
        return value < currentDate
    }

    override fun toString() = value.toString()
}
