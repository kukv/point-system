package jp.kukv.point.infrastructure.datasource.transaction

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.domain.model.transaction.TransactionType
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionNumberTable
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionTable
import org.jetbrains.exposed.sql.insert

object TransactionRegisterMapper {
    fun registerNumber(
        id: Id,
        transactionNumber: TransactionNumber,
        audit: Audit,
    ) {
        PointTransactionNumberTable.insert {
            it[PointTransactionNumberTable.transactionNumber] = transactionNumber()
            it[PointTransactionNumberTable.dinerId] = id()
            it[PointTransactionNumberTable.createdAt] = audit.persistenceTime()
            it[PointTransactionNumberTable.audit] = audit.auditor()
        }
    }

    fun registerTransaction(
        transactionNumber: TransactionNumber,
        transactionType: TransactionType,
        point: Point,
        audit: Audit,
    ) {
        PointTransactionTable.insert {
            it[PointTransactionTable.transactionNumber] = transactionNumber()
            it[PointTransactionTable.transactionType] = transactionType
            it[PointTransactionTable.point] = point()
            it[PointTransactionTable.createdAt] = audit.persistenceTime()
            it[PointTransactionTable.audit] = audit.auditor()
        }
    }
}
