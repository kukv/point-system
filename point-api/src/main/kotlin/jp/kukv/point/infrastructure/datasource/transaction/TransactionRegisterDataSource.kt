package jp.kukv.point.infrastructure.datasource.transaction

import jp.kukv.point.application.repository.transaction.TransactionRegisterRepository
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.domain.model.transaction.TransactionType
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class TransactionRegisterDataSource(private val database: Database) : TransactionRegisterRepository {
    override fun register(
        id: Id,
        transactionType: TransactionType,
        point: Point,
    ) {
        val transactionNumber = TransactionNumber.create()
        val audit = Audit.create(TransactionRegisterDataSource::class)

        transaction(database) {
            TransactionRegisterMapper.registerNumber(id, transactionNumber, audit)
            TransactionRegisterMapper
                .registerTransaction(transactionNumber, transactionType, point, audit)
        }
    }
}
