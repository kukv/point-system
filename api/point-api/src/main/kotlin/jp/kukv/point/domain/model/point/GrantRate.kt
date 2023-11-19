package jp.kukv.point.domain.model.point

import jp.kukv.point.domain.model.check.SubTotalAmount
import java.math.BigDecimal
import kotlin.jvm.JvmInline

/** ポイント付与率 */
@JvmInline
value class GrantRate private constructor(private val value: BigDecimal) {
    fun toPoint(subTotalAmount: SubTotalAmount): Point {
        val amount = BigDecimal.valueOf(subTotalAmount().toLong())
        amount.setScale(0)

        val point = amount.multiply(value)
        return Point(point.toInt())
    }

    override fun toString(): String = value.toPlainString()

    companion object {
        fun prototype(): GrantRate = GrantRate(BigDecimal.valueOf(0.01))
    }
}
