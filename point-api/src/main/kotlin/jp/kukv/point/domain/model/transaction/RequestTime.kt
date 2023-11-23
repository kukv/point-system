package jp.kukv.point.domain.model.transaction

import jp.kukv.point._extensions.kotlinx.datetime.now
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/** リクエスト受付日時 */
@JvmInline
@Serializable
value class RequestTime(private val value: LocalDateTime) {
    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object {
        fun prototype(): RequestTime = RequestTime(LocalDateTime.now())
    }
}
