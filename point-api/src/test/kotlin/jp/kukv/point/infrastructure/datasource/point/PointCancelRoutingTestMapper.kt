package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point._configuration.exposed.database
import jp.kukv.point.domain.model.transaction.TransactionType
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

object PointCancelRoutingTestMapper {
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
            // ポイント利用キャンセルできる
            OwnershipPointTable.insert {
                it[dinerId] = 1
                it[point] = 100
                it[expiredAt] = LocalDate.parse("2024-11-16")
                it[createdAt] = LocalDateTime.parse("2023-11-16T00:00:00")
                it[audit] = "test-data"
            }
            var id =
                OwnershipPointTable.insert {
                    it[dinerId] = 1
                    it[point] = 90
                    it[expiredAt] = LocalDate.parse("2024-11-16")
                    it[createdAt] = LocalDateTime.parse("2023-11-18T00:00:00")
                    it[audit] = "test-data"
                } get OwnershipPointTable.id
            ActiveOwnershipPointTable.insert {
                it[ownershipPointId] = id
            }

            PointTransactionNumberTable.insert {
                it[transactionNumber] = UUID.fromString("d5951ea5-9060-4331-9c45-b3a043730ced")
                it[dinerId] = 1
                it[createdAt] = LocalDateTime.parse("2023-11-16T00:00:00")
                it[audit] = "test-data"
            }
            PointTransactionNumberTable.insert {
                it[transactionNumber] = UUID.fromString("424f2509-640d-44d3-a983-045fcf4665e8")
                it[dinerId] = 1
                it[createdAt] = LocalDateTime.parse("2023-11-18T00:00:00")
                it[audit] = "test-data"
            }

            PointTransactionTable.insert {
                it[transactionNumber] = UUID.fromString("d5951ea5-9060-4331-9c45-b3a043730ced")
                it[transactionType] = TransactionType.獲得
                it[point] = 100
                it[createdAt] = LocalDateTime.parse("2023-11-16T00:00:00")
                it[audit] = "test-data"
            }
            PointTransactionTable.insert {
                it[transactionNumber] = UUID.fromString("424f2509-640d-44d3-a983-045fcf4665e8")
                it[transactionType] = TransactionType.利用
                it[point] = 10
                it[createdAt] = LocalDateTime.parse("2023-11-18T00:00:00")
                it[audit] = "test-data"
            }

            // ポイントの利用履歴が1ヶ月以上前の場合ポイント利用のキャンセルができない
            OwnershipPointTable.insert {
                it[dinerId] = 2
                it[point] = 500
                it[expiredAt] = LocalDate.parse("2024-11-16")
                it[createdAt] = LocalDateTime.parse("2023-11-16T00:00:00")
                it[audit] = "test-data"
            }
            id = OwnershipPointTable.insert {
                it[dinerId] = 2
                it[point] = 100
                it[expiredAt] = LocalDate.parse("2024-11-16")
                it[createdAt] = LocalDateTime.parse("2023-11-18T00:00:00")
                it[audit] = "test-data"
            } get OwnershipPointTable.id
            ActiveOwnershipPointTable.insert {
                it[ownershipPointId] = id
            }

            PointTransactionNumberTable.insert {
                it[transactionNumber] = UUID.fromString("b7805b78-f5c1-48a8-a679-a92a4c09a146")
                it[dinerId] = 2
                it[createdAt] = LocalDateTime.parse("2023-11-16T00:00:00")
                it[audit] = "test-data"
            }
            PointTransactionNumberTable.insert {
                it[transactionNumber] = UUID.fromString("f91d568e-2723-4c03-b807-ea35d98b7ec3")
                it[dinerId] = 2
                it[createdAt] = LocalDateTime.parse("2023-11-18T00:00:00")
                it[audit] = "test-data"
            }

            PointTransactionTable.insert {
                it[transactionNumber] = UUID.fromString("b7805b78-f5c1-48a8-a679-a92a4c09a146")
                it[transactionType] = TransactionType.獲得
                it[point] = 500
                it[createdAt] = LocalDateTime.parse("2023-11-16T00:00:00")
                it[audit] = "test-data"
            }
            PointTransactionTable.insert {
                it[transactionNumber] = UUID.fromString("f91d568e-2723-4c03-b807-ea35d98b7ec3")
                it[transactionType] = TransactionType.利用
                it[point] = 100
                it[createdAt] = LocalDateTime.parse("2023-11-18T00:00:00")
                it[audit] = "test-data"
            }

            // 保有ポイントの有効期限が切れている場合ポイント利用のキャンセルができない
            id = OwnershipPointTable.insert {
                it[dinerId] = 3
                it[point] = 1000
                it[expiredAt] = LocalDate.parse("2024-12-16")
                it[createdAt] = LocalDateTime.parse("2023-12-16T00:00:00")
                it[audit] = "test-data"
            } get OwnershipPointTable.id
            ActiveOwnershipPointTable.insert {
                it[ownershipPointId] = id
            }

            PointTransactionNumberTable.insert {
                it[transactionNumber] = UUID.fromString("8d4e7075-71c6-4ae8-b15d-267ff51dd244")
                it[dinerId] = 3
                it[createdAt] = LocalDateTime.parse("2023-12-16T00:00:00")
                it[audit] = "test-data"
            }

            PointTransactionTable.insert {
                it[transactionNumber] = UUID.fromString("8d4e7075-71c6-4ae8-b15d-267ff51dd244")
                it[transactionType] = TransactionType.獲得
                it[point] = 1000
                it[createdAt] = LocalDateTime.parse("2023-12-16T00:00:00")
                it[audit] = "test-data"
            }
        }
    }
}
