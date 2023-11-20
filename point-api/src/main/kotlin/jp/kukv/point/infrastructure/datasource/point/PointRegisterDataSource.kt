package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.application.repository.point.PointRegisterRepository
import jp.kukv.point.application.repository.point.PointRepository
import jp.kukv.point.application.repository.transaction.TransactionRegisterRepository
import jp.kukv.point.domain.model.check.SubTotalAmount
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.OwnershipPoint
import jp.kukv.point.domain.model.transaction.TransactionType
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class PointRegisterDataSource(
    private val database: Database,
    private val pointRepository: PointRepository,
    private val transactionRegisterRepository: TransactionRegisterRepository,
) : PointRegisterRepository {
    override fun register(
        id: Id,
        subTotalAmount: SubTotalAmount,
    ) {
        val current =
            runCatching { pointRepository.findBy(id) }
                .getOrDefault(OwnershipPoint.empty())
        val additionalOwnershipPoint = OwnershipPoint.create(subTotalAmount)
        val ownershipPoint = current.add(additionalOwnershipPoint)

        val audit = Audit.create(PointRegisterDataSource::class)

        transaction(database) {
            val activeId = PointRegisterMapper.register(id, ownershipPoint, audit)

            PointRegisterMapper.removeActive(id)
            PointRegisterMapper.registerActive(activeId)

            transactionRegisterRepository.register(id, TransactionType.獲得, additionalOwnershipPoint.point)
        }
    }
}
