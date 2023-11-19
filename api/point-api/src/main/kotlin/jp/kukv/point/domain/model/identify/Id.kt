package jp.kukv.point.domain.model.identify

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/** id */
@JvmInline
@Serializable
value class Id(private val value: Int) {
    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object {
        fun create(value: String): Id =
            runCatching { Id(value.toInt()) }
                .getOrElse { throw IllegalArgumentException("顧客IDは数値のみ") }

        val validator =
            validator {
                (Id::value)("id") {
                    greaterThanOrEqual(1).message("利用者IDは1以上")
                    lessThanOrEqual(2_000_000_000).message("利用者IDは2,000,000,000以下")
                }
            }
    }
}
