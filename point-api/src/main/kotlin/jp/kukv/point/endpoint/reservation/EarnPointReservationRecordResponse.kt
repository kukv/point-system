package jp.kukv.point.endpoint.reservation

import jp.kukv.point.domain.model.reservation.ReservationNumber
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class EarnPointReservationRecordResponse(
    @SerialName("reservation_number") private val reservationNumber: ReservationNumber,
) {
    override fun toString(): String {
        return "EarnPointReservationResponse(reservationNumber=$reservationNumber)"
    }
}
