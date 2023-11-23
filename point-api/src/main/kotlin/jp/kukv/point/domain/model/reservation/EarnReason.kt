package jp.kukv.point.domain.model.reservation

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/** ポイント付与理由 */
@JvmInline
@Serializable
value class EarnReason(private val value: String) {
    operator fun invoke() = value

    override fun toString() = value

    companion object {
        val validator =
            validator {
                (EarnReason::value)("ポイント付与理由") {
                    notBlank().message("ポイント付与理由が空文字")
                    greaterThanOrEqual(1).message("ポイント付与理由は1文字以上")
                    lessThanOrEqual(200).message("ポイント付与理由は200文字以下")
                }
            }
    }
}
