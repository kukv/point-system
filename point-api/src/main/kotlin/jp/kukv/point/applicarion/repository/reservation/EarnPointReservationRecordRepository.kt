package jp.kukv.point.applicarion.repository.reservation

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.payment.PaymentAmount
import jp.kukv.point.domain.model.reservation.EarnReason
import jp.kukv.point.domain.model.reservation.ReservationNumber

/**
 * 決済金額に応じたポイントの獲得予約を記録するリポジトリ
 */
interface EarnPointReservationRecordRepository {
    /**
     * 予約する
     */
    fun reservation(
        id: Id,
        reason: EarnReason,
        paymentAmount: PaymentAmount,
    ): ReservationNumber
}
