package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.configuration.exposed.database
import jp.kukv.point.domain.model.transaction.TransactionType
import jp.kukv.point.extensions.kotlinx.now
import jp.kukv.point.infrastructure.datasource.exposed.ActiveOwnershipPointTable
import jp.kukv.point.infrastructure.datasource.exposed.OwnershipPointTable
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionNumberTable
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionTable
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import java.util.UUID

object PointUseRoutingTestMapper {
    private val database = database()

    fun clear() {
        transaction(database) {
            ActiveOwnershipPointTable.deleteAll()
            OwnershipPointTable.deleteAll()

            PointTransactionTable.deleteAll()
            PointTransactionNumberTable.deleteAll()
        }
    }

    fun register() {
        transaction(database) {
            val activeId =
                OwnershipPointTable.insert {
                    it[dinerId] = 1
                    it[point] = 100
                    it[expiredAt] = LocalDate.parse("2024-11-01")
                    it[createdAt] = LocalDateTime.now()
                    it[audit] = "test-data"
                } get OwnershipPointTable.id
            ActiveOwnershipPointTable.insert {
                it[ownershipPointId] = activeId
            }

            PointTransactionNumberTable.insert {
                it[dinerId] = 1
                it[transactionNumber] = UUID.fromString("c1e0b604-cef1-4e5d-94b2-704ca17f261b")
                it[createdAt] = LocalDateTime.parse("2023-11-01T00:00:00")
                it[audit] = "test-data"
            }
            PointTransactionTable.insert {
                it[transactionNumber] = UUID.fromString("c1e0b604-cef1-4e5d-94b2-704ca17f261b")
                it[transactionType] = TransactionType.獲得
                it[point] = 100
                it[createdAt] = LocalDateTime.parse("2023-11-01T00:00:00")
                it[audit] = "test-data"
            }
        }
    }
}
