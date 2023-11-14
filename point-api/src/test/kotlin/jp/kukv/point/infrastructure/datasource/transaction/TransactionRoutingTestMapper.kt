package jp.kukv.point.infrastructure.datasource.transaction

import jp.kukv.point._configuration.exposed.database
import jp.kukv.point.domain.model.transaction.TransactionType
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionNumberTable
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionTable
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import java.util.UUID

object TransactionRoutingTestMapper {
    private val database = database()

    fun clear() {
        transaction(database) {
            PointTransactionTable.deleteAll()
            PointTransactionNumberTable.deleteAll()
        }
    }

    fun register() {
        transaction(database) {
            PointTransactionNumberTable.insert {
                it[dinerId] = 1
                it[transactionNumber] = UUID.fromString("d9a12a38-3d46-4862-a0a2-dee467028bdf")
                it[createdAt] = LocalDateTime.parse("2023-11-01T00:00:00")
                it[audit] = "test-data"
            }
            PointTransactionTable.insert {
                it[transactionNumber] = UUID.fromString("d9a12a38-3d46-4862-a0a2-dee467028bdf")
                it[transactionType] = TransactionType.獲得
                it[point] = 100
                it[createdAt] = LocalDateTime.parse("2023-11-01T00:00:00")
                it[audit] = "test-data"
            }

            PointTransactionNumberTable.insert {
                it[dinerId] = 1
                it[transactionNumber] = UUID.fromString("afaf72ac-7427-4538-8ea7-3dd9fddbb9c1")
                it[createdAt] = LocalDateTime.parse("2023-11-02T00:00:00")
                it[audit] = "test-data"
            }
            PointTransactionTable.insert {
                it[transactionNumber] = UUID.fromString("afaf72ac-7427-4538-8ea7-3dd9fddbb9c1")
                it[transactionType] = TransactionType.利用
                it[point] = 50
                it[createdAt] = LocalDateTime.parse("2023-11-02T00:00:00")
                it[audit] = "test-data"
            }

            PointTransactionNumberTable.insert {
                it[dinerId] = 1
                it[transactionNumber] = UUID.fromString("465a55d2-a143-4cdd-80d4-3c2cd7bc336c")
                it[createdAt] = LocalDateTime.parse("2023-11-03T00:00:00")
                it[audit] = "test-data"
            }
            PointTransactionTable.insert {
                it[transactionNumber] = UUID.fromString("465a55d2-a143-4cdd-80d4-3c2cd7bc336c")
                it[transactionType] = TransactionType.失効
                it[point] = 50
                it[createdAt] = LocalDateTime.parse("2023-11-03T00:00:00")
                it[audit] = "test-data"
            }
        }
    }
}
