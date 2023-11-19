package jp.kukv.menu.domain.model

import kotlin.jvm.JvmInline

/** 消費税 */
@JvmInline
value class Tax private constructor(private val value: Double) {
    override fun toString() = value.toString()

    companion object {
        fun prototype(): Tax = Tax(0.08)
    }
}
