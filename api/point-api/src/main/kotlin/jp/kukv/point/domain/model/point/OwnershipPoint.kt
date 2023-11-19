package jp.kukv.point.domain.model.point

import jp.kukv.point.domain.model.check.SubTotalAmount
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** 保有ポイント */
@Serializable
class OwnershipPoint(
    @SerialName("point") val point: Point,
    @SerialName("expired") val expirationPeriod: ExpirationPeriod,
) {
    fun add(ownershipPoint: OwnershipPoint): OwnershipPoint {
        var temporary = if (!expirationPeriod.isExpired()) point else Point.prototype()
        temporary += ownershipPoint.point

        return OwnershipPoint(temporary, ownershipPoint.expirationPeriod)
    }

    fun use(point: Point): OwnershipPoint {
        val rule = PointUsageRule(this, point)
        rule.check()

        val temporary = this.point - point
        return OwnershipPoint(temporary, expirationPeriod)
    }

    fun cancel(point: Point): OwnershipPoint {
        val rule = PointCancelRule(this)
        rule.check()

        val temporary = this.point + point
        return OwnershipPoint(temporary, expirationPeriod)
    }

    override fun toString(): String {
        return "OwnershipPoint(point=$point, expirationPeriod=$expirationPeriod)"
    }

    companion object {
        fun empty(): OwnershipPoint {
            val expirationPeriod = ExpirationPeriod.prototype()
            val point = Point.prototype()
            return OwnershipPoint(point, expirationPeriod)
        }

        fun create(subTotalAmount: SubTotalAmount): OwnershipPoint {
            val grantRate = GrantRate.prototype()
            val point = grantRate.toPoint(subTotalAmount)

            val expirationPeriod = ExpirationPeriod.prototype()
            return OwnershipPoint(point, expirationPeriod)
        }
    }
}
