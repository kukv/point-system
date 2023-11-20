package jp.kukv.point.domain.model.transaction

import jp.kukv.point.domain.model.point.Point
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 取引
 */
@Serializable
class Transaction(
    @SerialName("transaction_number") private val transactionNumber: TransactionNumber,
    @SerialName("point") val point: Point,
    @SerialName("type") private val transactionType: TransactionType,
    @SerialName("created_at") private val transactionTime: TransactionTime,
) {
    fun isキャンセル不可(): Boolean {
        if (transactionTime.is取引から1ヶ月以上経過()) return true
        if (!transactionType.isキャンセル可能な取引種別()) return true
        return false
    }

    override fun toString(): String {
        return "Transaction(" +
            "   transactionNumber=$transactionNumber, " +
            "   point=$point, transactionType=$transactionType, " +
            "   transactionTime=$transactionTime" +
            ")"
    }
}
