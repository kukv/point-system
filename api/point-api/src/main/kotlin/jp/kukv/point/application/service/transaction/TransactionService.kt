package jp.kukv.point.application.service.transaction

import jp.kukv.point.application.repository.transaction.TransactionRepository
import org.koin.core.annotation.Single

@Single
class TransactionService(
    private val transactionRepository: TransactionRepository,
) : TransactionRepository by transactionRepository
