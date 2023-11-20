package jp.kukv.point_expiration_execution.infrastructure.datasource.point

import jp.kukv.point_expiration_execution.domain.model.identify.Id
import jp.kukv.point_expiration_execution.domain.model.point.ExpirationPeriod
import jp.kukv.point_expiration_execution.domain.model.point.OwnershipPoint
import jp.kukv.point_expiration_execution.domain.model.point.Point
import jp.kukv.point_expiration_execution.infrastructure.datasource.exposed.ActiveOwnershipPointTable
import jp.kukv.point_expiration_execution.infrastructure.datasource.exposed.OwnershipPointTable
import org.jetbrains.exposed.sql.selectAll

object PointMapper {
    fun listAll(): Map<Id, OwnershipPoint> =
        (OwnershipPointTable innerJoin ActiveOwnershipPointTable)
            .slice(OwnershipPointTable.dinerId, OwnershipPointTable.expiredAt, OwnershipPointTable.point)
            .selectAll()
            .associate {
                val id = Id(it[OwnershipPointTable.dinerId])

                val expirationPeriod = ExpirationPeriod(it[OwnershipPointTable.expiredAt])
                val point = Point(it[OwnershipPointTable.point])

                id to OwnershipPoint(point, expirationPeriod)
            }
}
