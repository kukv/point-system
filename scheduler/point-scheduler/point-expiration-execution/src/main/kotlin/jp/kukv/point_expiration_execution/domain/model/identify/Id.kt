package jp.kukv.point_expiration_execution.domain.model.identify

import kotlin.jvm.JvmInline

/** id */
@JvmInline
value class Id(private val value: Int) {
    operator fun invoke() = value

    override fun toString() = value.toString()
}
