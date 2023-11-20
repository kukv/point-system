package jp.kukv.point_expiration_execution.domain.model.point

import jp.kukv.point_expiration_execution.domain.model.identify.Id
import jp.kukv.point_expiration_execution.domain.policy.ResourceNotfoundException

/**
 * 有効期限切れの顧客別保有ポイント一覧
 */
class ExpiredOwnershipPoints(private val map: Map<Id, OwnershipPoint>) {
    fun find(id: Id): OwnershipPoint = map[id] ?: throw ResourceNotfoundException("IDに該当するポイント情報が存在しない。")

    fun asKeys(): Set<Id> = map.keys

    fun size(): Int = map.size

    override fun toString(): String {
        return map.toString()
    }
}
