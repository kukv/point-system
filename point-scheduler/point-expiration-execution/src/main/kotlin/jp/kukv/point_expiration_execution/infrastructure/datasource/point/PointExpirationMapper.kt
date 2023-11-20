package jp.kukv.point_expiration_execution.infrastructure.datasource.point

import jp.kukv.point_expiration_execution.domain.model.identify.Id
import jp.kukv.point_expiration_execution.infrastructure.datasource.exposed.ActiveOwnershipPointTable
import jp.kukv.point_expiration_execution.infrastructure.datasource.exposed.OwnershipPointTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select

object PointExpirationMapper {
    fun removeActive(id: Id) {
        val activeId =
            (OwnershipPointTable innerJoin ActiveOwnershipPointTable)
                .slice(ActiveOwnershipPointTable.ownershipPointId)
                .select { OwnershipPointTable.dinerId eq id() }
                .last()[ActiveOwnershipPointTable.ownershipPointId]

        ActiveOwnershipPointTable.deleteWhere { ownershipPointId eq activeId }
    }
}
