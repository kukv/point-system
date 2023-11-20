package jp.kukv.point.domain.model.point

/**
 * ポイント利用キャンセル時のルールチェック
 */
class PointCancelRule(private val ownershipPoint: OwnershipPoint) {
    fun check() {
        val expirationDate = ownershipPoint.expirationPeriod
        if (expirationDate.isExpired()) {
            throw IllegalStateException("保有ポイントの有効期限切れ。")
        }
    }
}
