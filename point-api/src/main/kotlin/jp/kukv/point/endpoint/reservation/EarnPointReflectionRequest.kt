package jp.kukv.point.endpoint.reservation

import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations
import jp.kukv.point.domain.model.reservation.ReservationNumber
import jp.kukv.point.endpoint.Request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class EarnPointReflectionRequest(
    @SerialName("reservation_number") val reservationNumber: ReservationNumber,
) : Request<EarnPointReflectionRequest> {
    @Transient
    override val validator =
        validator {
            EarnPointReflectionRequest::reservationNumber nest ReservationNumber.validator
        }

    override fun validate(): ConstraintViolations = validator.validate(this)

    override fun toString(): String {
        return "EarnPointReflectionRequest(reservationNumber=$reservationNumber)"
    }
}
