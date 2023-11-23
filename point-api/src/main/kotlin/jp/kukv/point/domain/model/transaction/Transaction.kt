package jp.kukv.point.domain.model.transaction

import jp.kukv.point.domain.model.point.Point

/**
 * 取引
 */
class Transaction(
    val transactionNumber: TransactionNumber,
    val source: Source,
    val event: Event,
    val point: Point,
    val requestTime: RequestTime,
) {
    override fun toString(): String {
        return "Transaction(" +
            "transactionNumber=$transactionNumber, " +
            "source=$source, " +
            "event=$event, " +
            "point=$point, " +
            "requestTime=$requestTime" +
            ")"
    }
}
