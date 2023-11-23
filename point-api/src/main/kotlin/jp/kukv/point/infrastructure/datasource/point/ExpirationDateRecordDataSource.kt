package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.ExpirationPeriod
import jp.kukv.point.infrastructure.datasource.Audit
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database

class ExpirationDateRecordDataSource(private val database: Database) {
    fun record(id: Id) =
        transaction(database) {
            val expirationPeriod = ExpirationPeriod.prototype()
            val audit = Audit.create(ExpirationDateRecordDataSource::class)

            ExpirationDateRecordMapper.record(id, expirationPeriod, audit)
        }
}
