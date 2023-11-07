package jp.kukv.menu.domain.model

import kotlin.jvm.JvmInline

/** 税込価格 */
@JvmInline
value class Price(private val value: Int) {
    override fun toString() = value.toString()
}
