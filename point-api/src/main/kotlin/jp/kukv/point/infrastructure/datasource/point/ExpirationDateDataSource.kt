package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.ExpirationPeriod
import jp.kukv.point.domain.policy.exception.ResourceNotfoundException
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.Single

@Single
class ExpirationDateDataSource(private val database: Database) {
    fun findBy(id: Id): ExpirationPeriod {
        val expirationPeriod =
            transaction(database) {
                ExpirationDateMapper.findBy(id)
            } ?: throw ResourceNotfoundException("有効期限が存在しない。")

        return expirationPeriod
    }
}
