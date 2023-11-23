package jp.kukv.point.infrastructure.datasource.reservation

import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.reservation.EarnReason
import jp.kukv.point.domain.model.reservation.ReservationNumber
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.EarnPointReservationTable
import org.jetbrains.exposed.sql.insert

object EarnPointReservationRecordMapper {
    fun record(
        transactionNumber: TransactionNumber,
        reason: EarnReason,
        point: Point,
        audit: Audit,
    ): ReservationNumber {
        val reservationNumber =
            EarnPointReservationTable.insert {
                it[EarnPointReservationTable.transactionNumber] = transactionNumber()
                it[EarnPointReservationTable.reason] = reason()
                it[EarnPointReservationTable.point] = point()
                it[EarnPointReservationTable.createdAt] = audit.persistenceTime()
                it[EarnPointReservationTable.created] = audit.auditor()
            } get EarnPointReservationTable.id

        return ReservationNumber(reservationNumber)
    }
}
