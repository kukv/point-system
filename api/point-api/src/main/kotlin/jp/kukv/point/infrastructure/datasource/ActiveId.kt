package jp.kukv.point.infrastructure.datasource

import kotlin.jvm.JvmInline

@JvmInline
value class ActiveId(private val value: Int) {
    operator fun invoke() = value

    override fun toString() = value.toString()
}
