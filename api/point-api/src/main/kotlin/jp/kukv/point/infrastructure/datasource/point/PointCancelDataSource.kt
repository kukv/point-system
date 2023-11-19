package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.application.repository.point.PointCancelRepository
import jp.kukv.point.application.repository.point.PointRepository
import jp.kukv.point.application.repository.transaction.TransactionRegisterRepository
import jp.kukv.point.application.repository.transaction.TransactionRepository
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.domain.model.transaction.TransactionType
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class PointCancelDataSource(
    private val database: Database,
    private val pointRepository: PointRepository,
    private val transactionRepository: TransactionRepository,
    private val transactionRegisterRepository: TransactionRegisterRepository,
) : PointCancelRepository {
    override fun cancel(
        id: Id,
        transactionNumber: TransactionNumber,
    ) {
        val transaction = transactionRepository.findBy(id, transactionNumber)
        if (transaction.isキャンセル不可()) {
            throw IllegalStateException("ポイント取引のキャンセル不可。")
        }

        val currentOwnershipPoint = pointRepository.findBy(id)
        val canceledOwnershipPoint = currentOwnershipPoint.cancel(transaction.point)

        val audit = Audit.create(PointCancelDataSource::class)

        transaction(database) {
            val activeId = PointRegisterMapper.register(id, canceledOwnershipPoint, audit)

            PointRegisterMapper.removeActive(id)
            PointRegisterMapper.registerActive(activeId)

            transactionRegisterRepository.register(id, TransactionType.キャンセル, transaction.point)
        }
    }
}
