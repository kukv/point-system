package jp.kukv.point.domain.model.payment

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import kotlin.jvm.JvmInline

/** 決済金額 */
@JvmInline
@Serializable
value class PaymentAmount(private val value: Int) {
    fun toBigDecimal(): BigDecimal = BigDecimal.valueOf(value.toLong(), 0)

    override fun toString() = value.toString()

    companion object {
        val validator =
            validator {
                (PaymentAmount::value)("決済金額") {
                    greaterThanOrEqual(1).message("決済金額は1円以上")
                    lessThanOrEqual(99_999_999).message("決済金額は99,999,999円以下")
                }
            }
    }
}
