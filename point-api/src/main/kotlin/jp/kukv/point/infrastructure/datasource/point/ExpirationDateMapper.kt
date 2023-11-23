package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.ExpirationPeriod
import jp.kukv.point.infrastructure.datasource.exposed.ExpirationDateTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select

object ExpirationDateMapper {
    fun findBy(id: Id): ExpirationPeriod? {
        val resultRow =
            ExpirationDateTable
                .select { ExpirationDateTable.buyerId eq id() }
                .orderBy(ExpirationDateTable.id, order = SortOrder.DESC)
                .firstOrNull() ?: return null

        return ExpirationPeriod(resultRow[ExpirationDateTable.expiredAt])
    }
}
