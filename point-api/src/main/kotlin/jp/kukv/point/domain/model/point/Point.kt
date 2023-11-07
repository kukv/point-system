package jp.kukv.point.domain.model.point

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/** ポイント */
@JvmInline
@Serializable
value class Point(private val value: Int) {
    fun hasMore(point: Point): Boolean = value >= point.value

    operator fun plus(point: Point): Point {
        return Point(value + point.value)
    }

    operator fun minus(point: Point): Point {
        return Point(value - point.value)
    }

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
