package jp.kukv.point.infrastructure.datasource

@JvmInline
value class Auditor(private val value: String) {
    operator fun invoke(): String = value

    override fun toString() = value
}
