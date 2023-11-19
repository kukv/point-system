package jp.kukv.menu.domain.model

import kotlin.jvm.JvmInline

/** 料理名 */
@JvmInline
value class CookingName(private val value: String) {
    operator fun invoke() = value

    override fun toString() = value
}
