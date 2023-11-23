package jp.kukv.point.domain.model.point

import am.ik.yavi.builder.validator
import kotlin.jvm.JvmInline

/** ポイント */
@JvmInline
value class Point(private val value: Int) {
    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object {
        fun prototype(): Point = Point(0)

        val validator =
            validator {
                (Point::value)("ポイント") {
                    greaterThanOrEqual(1).message("ポイントは1以上")
                    lessThanOrEqual(10_000_000).message("ポイントは10,000,000以下")
                }
            }
    }
}
