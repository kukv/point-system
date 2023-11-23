package jp.kukv.point.infrastructure.datasource.reservation

import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.reservation.EarnPointReservation
import jp.kukv.point.domain.model.reservation.EarnReason
import jp.kukv.point.domain.model.reservation.ReservationNumber
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.infrastructure.datasource.exposed.EarnPointReservationTable
import org.jetbrains.exposed.sql.select

object EarnPointReservationMapper {
    fun findBy(reservationNumber: ReservationNumber): EarnPointReservation? {
        val resultRow =
            EarnPointReservationTable
                .select { EarnPointReservationTable.id eq reservationNumber() }
                .firstOrNull() ?: return null

        val transactionNumber = TransactionNumber(resultRow[EarnPointReservationTable.transactionNumber])
        val reason = EarnReason(resultRow[EarnPointReservationTable.reason])
        val point = Point(resultRow[EarnPointReservationTable.point])

        return EarnPointReservation(transactionNumber, reason, point)
    }
}
