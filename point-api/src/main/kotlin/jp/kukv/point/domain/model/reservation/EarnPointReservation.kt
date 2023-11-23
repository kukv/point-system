package jp.kukv.point.domain.model.reservation

import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.transaction.Event
import jp.kukv.point.domain.model.transaction.RequestTime
import jp.kukv.point.domain.model.transaction.Source
import jp.kukv.point.domain.model.transaction.Transaction
import jp.kukv.point.domain.model.transaction.TransactionNumber

/**
 * 獲得ポイントの予約情報
 */
class EarnPointReservation(
    private val transactionNumber: TransactionNumber,
    private val reason: EarnReason,
    private val point: Point,
) {
    fun toTransaction(): Transaction {
        val source = Source(reason())
        val event = Event.獲得
        val requestTime = RequestTime.prototype()

        return Transaction(transactionNumber, source, event, point, requestTime)
    }

    override fun toString(): String {
        return "EarnPointReservation(" +
            "transactionNumber=$transactionNumber, " +
            "reason=$reason, " +
            "point=$point" +
            ")"
    }
}
