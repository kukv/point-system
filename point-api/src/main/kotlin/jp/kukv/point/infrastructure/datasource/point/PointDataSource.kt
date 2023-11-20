package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.application.repository.point.PointRepository
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.OwnershipPoint
import jp.kukv.point.domain.policy.ResourceNotfoundException
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class PointDataSource(private val database: Database) : PointRepository {
    override fun findBy(id: Id): OwnershipPoint {
        val ownershipPoint =
            transaction(database) {
                PointMapper.findBy(id)
            } ?: throw ResourceNotfoundException("保有ポイントが存在しません。")

        return ownershipPoint
    }
}
