package jp.kukv.point.infrastructure.datasource.transaction

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class PointTransactionNumberRecordDataSource(private val database: Database) : PointTransactionNumberRecordRepository {
    override fun create(id: Id): TransactionNumber {
        val audit = Audit.create(PointTransactionNumberRecordDataSource::class)

        val transactionNumber = TransactionNumber.create()
        transaction(database) {
            PointTransactionNumberRecordMapper.create(id, transactionNumber, audit)
        }

        return transactionNumber
    }
}
