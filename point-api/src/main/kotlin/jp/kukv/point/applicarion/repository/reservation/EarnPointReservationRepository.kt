package jp.kukv.point.applicarion.repository.reservation

import jp.kukv.point.domain.model.reservation.EarnPointReservation
import jp.kukv.point.domain.model.reservation.ReservationNumber

interface EarnPointReservationRepository {
    fun findBy(reservationNumber: ReservationNumber): EarnPointReservation
}
