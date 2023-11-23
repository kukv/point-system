package jp.kukv.point.infrastructure.datasource.reservation

import jp.kukv.point.applicarion.repository.reservation.EarnPointReservationRepository
import jp.kukv.point.domain.model.reservation.EarnPointReservation
import jp.kukv.point.domain.model.reservation.ReservationNumber
import jp.kukv.point.domain.policy.exception.ResourceNotfoundException
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class EarnPointReservationDataSource(private val database: Database) : EarnPointReservationRepository {
    override fun findBy(reservationNumber: ReservationNumber): EarnPointReservation {
        val earnPointReservation =
            transaction(database) {
                EarnPointReservationMapper.findBy(reservationNumber)
            } ?: throw ResourceNotfoundException("獲得ポイントの予約情報が存在しない。")

        return earnPointReservation
    }
}
