package jp.kukv.point.infrastructure.datasource

import jp.kukv.point._extensions.kotlinx.now
import kotlinx.datetime.LocalDateTime

@JvmInline
value class PersistenceTime(private val value: LocalDateTime) {
    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object {
        fun now(): PersistenceTime = PersistenceTime(LocalDateTime.now())
    }
}
