package jp.kukv.point.infrastructure.datasource.transaction

import jp.kukv.point.application.repository.transaction.TransactionRepository
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.transaction.Transaction
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.domain.model.transaction.Transactions
import jp.kukv.point.domain.policy.ResourceNotfoundException
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class TransactionDataSource(private val database: Database) : TransactionRepository {
    override fun listOf(id: Id): Transactions {
        val transactions = transaction(database) { TransactionMapper.listOf(id) }
        return Transactions(transactions)
    }

    override fun findBy(
        id: Id,
        transactionNumber: TransactionNumber,
    ): Transaction {
        val transaction =
            transaction(database) {
                TransactionMapper.findBy(id, transactionNumber)
            } ?: throw ResourceNotfoundException("ポイント取引履歴が存在しません。")

        return transaction
    }
}
