package jp.kukv.point.endpoint.reservation

import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.payment.PaymentAmount
import jp.kukv.point.domain.model.reservation.EarnReason
import jp.kukv.point.endpoint.Request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class EarnPointReservationRecordRequest(
    @SerialName("id") val id: Id,
    @SerialName("reason") val reason: EarnReason,
    @SerialName("amount") val paymentAmount: PaymentAmount,
) : Request<EarnPointReservationRecordRequest> {
    @Transient
    override val validator =
        validator {
            EarnPointReservationRecordRequest::id nest Id.validator
            EarnPointReservationRecordRequest::reason nest EarnReason.validator
            EarnPointReservationRecordRequest::paymentAmount nest PaymentAmount.validator
        }

    override fun validate(): ConstraintViolations = validator.validate(this)

    override fun toString(): String {
        return "EarnPointReservationRequest(" +
            "id=$id, " +
            "reason=$reason, " +
            "paymentAmount=$paymentAmount, " +
            "validator=$validator" +
            ")"
    }
}
