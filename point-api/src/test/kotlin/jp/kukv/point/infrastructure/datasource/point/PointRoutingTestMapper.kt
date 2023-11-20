package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point._configuration.exposed.database
import jp.kukv.point._extensions.kotlinx.now
import jp.kukv.point.infrastructure.datasource.exposed.ActiveOwnershipPointTable
import jp.kukv.point.infrastructure.datasource.exposed.OwnershipPointTable
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert

object PointRoutingTestMapper {
    private val database = database()

    fun clear() {
        transaction(database) {
            ActiveOwnershipPointTable.deleteAll()
            OwnershipPointTable.deleteAll()
        }
    }

    fun register() {
        transaction(database) {
            val activeId =
                OwnershipPointTable.insert {
                    it[dinerId] = 1
                    it[point] = 100
                    it[expiredAt] = LocalDate.parse("2023-11-01")
                    it[createdAt] = LocalDateTime.now()
                    it[audit] = "test-data"
                } get OwnershipPointTable.id
            ActiveOwnershipPointTable.insert {
                it[ownershipPointId] = activeId
            }
        }
    }
}
