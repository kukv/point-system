package jp.kukv.point_expiration_execution.domain.model.transaction

import java.util.UUID
import kotlin.jvm.JvmInline

/** 取引番号 */
@JvmInline
value class TransactionNumber private constructor(private val value: String) {
    constructor(value: UUID) : this(value.toString())

    operator fun invoke(): UUID = UUID.fromString(value)

    override fun toString() = value

    companion object {
        fun create(): TransactionNumber = TransactionNumber(UUID.randomUUID().toString())
    }
}
