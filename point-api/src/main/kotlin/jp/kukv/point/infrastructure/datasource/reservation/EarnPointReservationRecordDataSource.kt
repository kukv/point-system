package jp.kukv.point.infrastructure.datasource.reservation

import jp.kukv.point.applicarion.repository.reservation.EarnPointReservationRecordRepository
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.payment.PaymentAmount
import jp.kukv.point.domain.model.point.GrantRate
import jp.kukv.point.domain.model.reservation.EarnReason
import jp.kukv.point.domain.model.reservation.ReservationNumber
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import jp.kukv.point.infrastructure.datasource.transaction.PointTransactionNumberRecordRepository
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class EarnPointReservationRecordDataSource(
    private val database: Database,
    private val pointTransactionNumberRecordRepository: PointTransactionNumberRecordRepository,
) : EarnPointReservationRecordRepository {
    override fun reservation(
        id: Id,
        reason: EarnReason,
        paymentAmount: PaymentAmount,
    ): ReservationNumber {
        val audit = Audit.create(EarnPointReservationRecordDataSource::class)

        val grantRate = GrantRate.prototype()
        val point = grantRate.toPoint(paymentAmount)

        val transactionNumber = pointTransactionNumberRecordRepository.create(id)

        return transaction(database) {
            EarnPointReservationRecordMapper.record(transactionNumber, reason, point, audit)
        }
    }
}
