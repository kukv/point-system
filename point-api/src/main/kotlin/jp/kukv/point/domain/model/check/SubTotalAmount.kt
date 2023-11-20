package jp.kukv.point.domain.model.check

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/** 小計 */
@JvmInline
@Serializable
value class SubTotalAmount(private val value: Int) {
    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object {
        val validator =
            validator {
                (SubTotalAmount::value)("小計") {
                    greaterThanOrEqual(1).message("小計は1円以上")
                    lessThanOrEqual(99_999_999).message("小計は99,999,999円以下")
                }
            }
    }
}
