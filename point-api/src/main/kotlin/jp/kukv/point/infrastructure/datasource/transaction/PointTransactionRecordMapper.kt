package jp.kukv.point.infrastructure.datasource.transaction

import jp.kukv.point.domain.model.transaction.Transaction
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionTable
import org.jetbrains.exposed.sql.insert

object PointTransactionRecordMapper {
    fun record(
        transaction: Transaction,
        audit: Audit,
    ) {
        PointTransactionTable.insert {
            it[PointTransactionTable.transactionNumber] = transaction.transactionNumber()
            it[PointTransactionTable.transactionSource] = transaction.source()
            it[PointTransactionTable.requestedAt] = transaction.requestTime()
            it[PointTransactionTable.point] = transaction.point()
            it[PointTransactionTable.createdAt] = audit.persistenceTime()
            it[PointTransactionTable.created] = audit.auditor()
        }
    }
}
