package jp.kukv.point.infrastructure.datasource.transaction

import jp.kukv.point.applicarion.repository.transaction.PointTransactionRecordRepository
import jp.kukv.point.domain.model.transaction.Transaction
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class PointTransactionRecordDataSource(private val database: Database) : PointTransactionRecordRepository {
    override fun record(transaction: Transaction) {
        val audit = Audit.create(PointTransactionRecordDataSource::class)
        transaction(database) {
            PointTransactionRecordMapper.record(transaction, audit)
        }
    }
}
