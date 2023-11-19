package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.OwnershipPoint
import jp.kukv.point.infrastructure.datasource.ActiveId
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.ActiveOwnershipPointTable
import jp.kukv.point.infrastructure.datasource.exposed.OwnershipPointTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

object PointRegisterMapper {
    fun register(
        id: Id,
        ownershipPoint: OwnershipPoint,
        audit: Audit,
    ): ActiveId {
        val activeId =
            OwnershipPointTable.insert {
                it[OwnershipPointTable.dinerId] = id()
                it[OwnershipPointTable.point] = ownershipPoint.point()
                it[OwnershipPointTable.expiredAt] = ownershipPoint.expirationPeriod()
                it[OwnershipPointTable.createdAt] = audit.persistenceTime()
                it[OwnershipPointTable.audit] = audit.auditor()
            } get OwnershipPointTable.id

        return ActiveId(activeId)
    }

    fun removeActive(id: Id) {
        val activeId =
            (OwnershipPointTable innerJoin ActiveOwnershipPointTable)
                .slice(OwnershipPointTable.id)
                .select { OwnershipPointTable.dinerId eq id() }
                .lastOrNull()?.get(OwnershipPointTable.id)

        if (activeId == null) return

        ActiveOwnershipPointTable.deleteWhere { ownershipPointId eq activeId }
    }

    fun registerActive(activeId: ActiveId) {
        ActiveOwnershipPointTable.insert {
            it[ActiveOwnershipPointTable.ownershipPointId] = activeId()
        }
    }
}
