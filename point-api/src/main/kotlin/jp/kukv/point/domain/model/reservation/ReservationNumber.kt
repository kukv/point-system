package jp.kukv.point.domain.model.reservation

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/** 予約番号 */
@JvmInline
@Serializable
value class ReservationNumber(private val value: Int) {
    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object {
        val validator =
            validator {
                (ReservationNumber::value)("予約番号") {
                    greaterThanOrEqual(1).message("予約番号は1以上")
                    lessThanOrEqual(2_000_000_000).message("予約番号は2,000,000,000以下")
                }
            }
    }
}
