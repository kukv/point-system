package jp.kukv.point.application.repository.transaction

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.transaction.Transaction
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.domain.model.transaction.Transactions

interface TransactionRepository {
    fun listOf(id: Id): Transactions

    fun findBy(
        id: Id,
        transactionNumber: TransactionNumber,
    ): Transaction
}
