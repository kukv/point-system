package jp.kukv.point.infrastructure.datasource.transaction

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.transaction.TransactionNumber

/**
 * 取引番号を生成するリポジトリ
 */
interface PointTransactionNumberRecordRepository {
    /**
     * 生成する
     */
    fun create(id: Id): TransactionNumber
}
