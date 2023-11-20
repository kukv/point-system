package jp.kukv.point.domain.model.transaction

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 取引一覧
 */
@Serializable
class Transactions(
    @SerialName("transactions") private val list: List<Transaction>,
) {
    override fun toString(): String {
        return "Transactions(list=$list)"
    }
}
