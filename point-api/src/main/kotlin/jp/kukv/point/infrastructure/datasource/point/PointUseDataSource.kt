package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.application.repository.point.PointRepository
import jp.kukv.point.application.repository.point.PointUseRepository
import jp.kukv.point.application.repository.transaction.TransactionRegisterRepository
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.transaction.TransactionType
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class PointUseDataSource(
    private val database: Database,
    private val pointRepository: PointRepository,
    private val transactionRegisterRepository: TransactionRegisterRepository,
) : PointUseRepository {
    override fun use(
        id: Id,
        point: Point,
    ) {
        val current = pointRepository.findBy(id)

        val usedOwnershipPoint = current.use(point)
        val audit = Audit.create(PointUseDataSource::class)

        transaction(database) {
            val activeId = PointRegisterMapper.register(id, usedOwnershipPoint, audit)

            PointRegisterMapper.removeActive(id)
            PointRegisterMapper.registerActive(activeId)

            transactionRegisterRepository.register(id, TransactionType.利用, point)
        }
    }
}
