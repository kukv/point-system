package jp.kukv.point.endpoint.point

import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations
import jp.kukv.point.domain.model.check.SubTotalAmount
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.endpoint.Request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class PointRegisterRequest(
    @SerialName("id") val id: Id,
    @SerialName("amount") val subTotalAmount: SubTotalAmount,
) :
    Request<PointRegisterRequest> {
    @Transient
    override val validator =
        validator {
            PointRegisterRequest::id nest Id.validator
            PointRegisterRequest::subTotalAmount nest SubTotalAmount.validator
        }

    override fun validate(): ConstraintViolations = validator.validate(this)
}
