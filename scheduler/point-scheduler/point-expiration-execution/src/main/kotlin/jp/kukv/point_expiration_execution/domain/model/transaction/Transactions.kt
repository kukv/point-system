package jp.kukv.point_expiration_execution.domain.model.transaction

/**
 * 取引一覧
 */
class Transactions(
    private val list: List<Transaction>,
) {
    override fun toString(): String {
        return "Transactions(list=$list)"
    }
}
