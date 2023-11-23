package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.ExpirationPeriod
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.ExpirationDateTable
import org.jetbrains.exposed.sql.insert

object ExpirationDateRecordMapper {
    fun record(
        id: Id,
        expirationPeriod: ExpirationPeriod,
        audit: Audit,
    ) {
        ExpirationDateTable.insert {
            it[ExpirationDateTable.buyerId] = id()
            it[ExpirationDateTable.expiredAt] = expirationPeriod()
            it[ExpirationDateTable.createdAt] = audit.persistenceTime()
            it[ExpirationDateTable.created] = audit.auditor()
        }
    }
}
