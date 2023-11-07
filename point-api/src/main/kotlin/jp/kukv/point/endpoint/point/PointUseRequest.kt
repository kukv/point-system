package jp.kukv.point.endpoint.point

import am.ik.yavi.builder.validator
import am.ik.yavi.core.ConstraintViolations
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.endpoint.Request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class PointUseRequest(
    @SerialName("id") val id: Id,
    @SerialName("point") val point: Point,
) :
    Request<PointUseRequest> {
    @Transient
    override val validator =
        validator {
            PointUseRequest::id nest Id.validator
            PointUseRequest::point nest Point.validator
        }

    override fun validate(): ConstraintViolations = validator.validate(this)
}
