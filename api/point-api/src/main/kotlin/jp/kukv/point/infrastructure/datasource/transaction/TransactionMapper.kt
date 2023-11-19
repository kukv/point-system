package jp.kukv.point.infrastructure.datasource.transaction

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.transaction.Transaction
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.domain.model.transaction.TransactionTime
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionNumberTable
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select

object TransactionMapper {
    fun listOf(id: Id): List<Transaction> =
        (PointTransactionNumberTable innerJoin PointTransactionTable)
            .slice(
                PointTransactionTable.transactionNumber,
                PointTransactionTable.transactionType,
                PointTransactionTable.point,
                PointTransactionTable.createdAt,
            )
            .select { PointTransactionNumberTable.dinerId eq id() }
            .map {
                val transactionNumber = TransactionNumber(it[PointTransactionTable.transactionNumber])
                val point = Point(it[PointTransactionTable.point])
                val transactionType = it[PointTransactionTable.transactionType]
                val transactionTime = TransactionTime(it[PointTransactionTable.createdAt])

                Transaction(transactionNumber, point, transactionType, transactionTime)
            }

    fun findBy(
        id: Id,
        transactionNumber: TransactionNumber,
    ): Transaction? {
        val resultRow =
            (PointTransactionNumberTable innerJoin PointTransactionTable)
                .slice(
                    PointTransactionTable.transactionNumber,
                    PointTransactionTable.transactionType,
                    PointTransactionTable.point,
                    PointTransactionTable.createdAt,
                )
                .select {
                    (PointTransactionNumberTable.dinerId eq id()) and
                        (PointTransactionNumberTable.transactionNumber eq transactionNumber())
                }
                .firstOrNull()

        if (resultRow == null) return null

        val resultTransactionNumber = TransactionNumber(resultRow[PointTransactionTable.transactionNumber])
        val point = Point(resultRow[PointTransactionTable.point])
        val transactionType = resultRow[PointTransactionTable.transactionType]
        val transactionTime = TransactionTime(resultRow[PointTransactionTable.createdAt])

        return Transaction(resultTransactionNumber, point, transactionType, transactionTime)
    }
}
