package jp.kukv.point.domain.model.point

/**
 * ポイント利用時のルール
 */
class PointUsageRule(private val ownershipPoint: OwnershipPoint, private val point: Point) {
    fun check() {
        val currentPoint = ownershipPoint.point
        if (!currentPoint.hasMore(point)) {
            throw IllegalStateException("保有ポイントが不足している。")
        }

        val currentExpirationDate = ownershipPoint.expirationPeriod
        if (currentExpirationDate.isExpired()) {
            throw IllegalStateException("保有ポイントの有効期限切れ。")
        }
    }
}
