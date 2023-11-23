package jp.kukv.point.applicarion.repository.transaction

import jp.kukv.point.domain.model.transaction.Transaction

interface PointTransactionRecordRepository {
    fun record(transaction: Transaction)
}
