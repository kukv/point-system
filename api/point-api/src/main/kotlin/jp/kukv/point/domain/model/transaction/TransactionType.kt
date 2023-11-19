package jp.kukv.point.domain.model.transaction

import kotlinx.serialization.Serializable

/**
 * 取引種別
 */
@Serializable
enum class TransactionType {
    獲得,
    利用,
    失効,
    キャンセル,
    ;

    fun isキャンセル可能な取引種別(): Boolean {
        if (this == 利用) return true
        if (this == 獲得) return true
        return false
    }
}
