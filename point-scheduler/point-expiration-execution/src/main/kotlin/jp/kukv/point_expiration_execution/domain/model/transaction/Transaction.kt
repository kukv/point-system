package jp.kukv.point_expiration_execution.domain.model.transaction

import jp.kukv.point_expiration_execution.domain.model.point.Point

/**
 * 取引
 */
class Transaction(
    private val transactionNumber: TransactionNumber,
    private val point: Point,
    private val transactionType: TransactionType,
    private val transactionTime: TransactionTime,
) {
    override fun toString(): String {
        return "Transaction(" +
            "   transactionNumber=$transactionNumber, " +
            "   point=$point, transactionType=$transactionType, " +
            "   transactionTime=$transactionTime" +
            ")"
    }
}
