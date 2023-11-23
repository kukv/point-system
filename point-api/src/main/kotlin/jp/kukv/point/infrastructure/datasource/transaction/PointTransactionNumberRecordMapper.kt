package jp.kukv.point.infrastructure.datasource.transaction

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionNumberTable
import org.jetbrains.exposed.sql.insert

object PointTransactionNumberRecordMapper {
    fun create(
        id: Id,
        transactionNumber: TransactionNumber,
        audit: Audit,
    ) {
        PointTransactionNumberTable.insert {
            it[PointTransactionNumberTable.transactionNumber] = transactionNumber()
            it[PointTransactionNumberTable.buyerId] = id()
            it[PointTransactionNumberTable.createdAt] = audit.persistenceTime()
            it[PointTransactionNumberTable.created] = audit.auditor()
        }
    }
}
