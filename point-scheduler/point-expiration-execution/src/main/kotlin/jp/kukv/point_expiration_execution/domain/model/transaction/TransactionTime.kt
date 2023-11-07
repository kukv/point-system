package jp.kukv.point_expiration_execution.domain.model.transaction

import kotlinx.datetime.LocalDateTime
import kotlin.jvm.JvmInline

/** 取引日時 */
@JvmInline
value class TransactionTime(private val value: LocalDateTime) {
    override fun toString() = value.toString()
}
