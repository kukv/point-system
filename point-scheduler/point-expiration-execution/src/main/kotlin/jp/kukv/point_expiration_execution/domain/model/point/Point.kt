package jp.kukv.point_expiration_execution.domain.model.point

import kotlin.jvm.JvmInline

/** ポイント */
@JvmInline
value class Point(private val value: Int) {
    operator fun invoke() = value

    override fun toString() = value.toString()
}
