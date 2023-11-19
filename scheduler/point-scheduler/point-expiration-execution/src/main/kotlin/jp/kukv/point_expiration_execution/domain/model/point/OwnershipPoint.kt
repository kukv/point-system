package jp.kukv.point_expiration_execution.domain.model.point

/** 保有ポイント */
class OwnershipPoint(
    val point: Point,
    val expirationPeriod: ExpirationPeriod,
) {
    override fun toString(): String {
        return "OwnershipPoint(point=$point, expirationPeriod=$expirationPeriod)"
    }
}
