package jp.kukv.menu.domain.model

import kotlin.jvm.JvmInline

/** カテゴリ */
@JvmInline
value class Category(private val value: String) {
    override fun toString() = value
}
