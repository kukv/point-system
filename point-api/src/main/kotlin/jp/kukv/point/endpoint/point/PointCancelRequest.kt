package jp.kukv.point.endpoint.point

import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.endpoint.Request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class PointCancelRequest(
    @SerialName("id") val id: Id,
    @SerialName("transaction_number") val transactionNumber: TransactionNumber,
) : Request<PointCancelRequest> {
    @Transient
    override val validator =
        validator {
            PointCancelRequest::id nest Id.validator
            PointCancelRequest::transactionNumber nest TransactionNumber.validator
        }

    override fun validate(): ConstraintViolations = validator.validate(this)

    override fun toString(): String {
        return "PointCancelRequest(id=$id, transactionNumber=$transactionNumber, validator=$validator)"
    }
}
