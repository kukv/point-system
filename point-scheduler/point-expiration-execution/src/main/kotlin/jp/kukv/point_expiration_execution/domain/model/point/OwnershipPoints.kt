package jp.kukv.point_expiration_execution.domain.model.point

import jp.kukv.point_expiration_execution.domain.model.identify.Id

/**
 * 顧客別保有ポイント一覧
 */
class OwnershipPoints(private val map: Map<Id, OwnershipPoint>) {
    fun toExpiredOwnershipPoints(): ExpiredOwnershipPoints {
        val expired =
            map.filter {
                val ownershipPoint = it.value
                val expirationDate = ownershipPoint.expirationPeriod
                expirationDate.isExpired()
            }
        return ExpiredOwnershipPoints(expired)
    }

    override fun toString(): String {
        return map.toString()
    }
}
