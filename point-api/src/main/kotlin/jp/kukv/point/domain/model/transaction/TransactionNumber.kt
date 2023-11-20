package jp.kukv.point.domain.model.transaction

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.jvm.JvmInline

/** 取引番号 */
@JvmInline
@Serializable
value class TransactionNumber private constructor(private val value: String) {
    constructor(value: UUID) : this(value.toString())

    operator fun invoke(): UUID = UUID.fromString(value)

    override fun toString() = value

    companion object {
        fun create(): TransactionNumber = TransactionNumber(UUID.randomUUID().toString())

        val validator =
            validator {
                (TransactionNumber::value)("取引番号") {
                    uuid().message("取引番号はUUIDのみ")
                }
            }
    }
}
