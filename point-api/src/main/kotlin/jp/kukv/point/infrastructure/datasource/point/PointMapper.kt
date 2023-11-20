package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.ExpirationPeriod
import jp.kukv.point.domain.model.point.OwnershipPoint
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.infrastructure.datasource.exposed.ActiveOwnershipPointTable
import jp.kukv.point.infrastructure.datasource.exposed.OwnershipPointTable
import org.jetbrains.exposed.sql.select

object PointMapper {
    fun findBy(id: Id): OwnershipPoint? {
        val resultRow =
            (OwnershipPointTable innerJoin ActiveOwnershipPointTable)
                .slice(OwnershipPointTable.point, OwnershipPointTable.expiredAt)
                .select { OwnershipPointTable.dinerId eq id() }
                .firstOrNull()

        if (resultRow == null) return null

        val point = Point(resultRow[OwnershipPointTable.point])
        val expirationPeriod = ExpirationPeriod(resultRow[OwnershipPointTable.expiredAt])
        return OwnershipPoint(point, expirationPeriod)
    }
}
