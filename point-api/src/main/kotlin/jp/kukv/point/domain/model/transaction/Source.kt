package jp.kukv.point.domain.model.transaction

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/** 取引発生元 */
@JvmInline
@Serializable
value class Source(private val value: String) {
    operator fun invoke() = value

    override fun toString() = value

    companion object {
        val validator =
            validator {
                (Source::value)("取引発生元") {
                    notBlank().message("取引発生元が空文字")
                    greaterThanOrEqual(1).message("取引発生元は1文字以上")
                    lessThanOrEqual(200).message("取引発生元は200文字以下")
                }
            }
    }
}
