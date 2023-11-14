package jp.kukv.point.infrastructure.datasource.point

import jp.kukv.point.configuration.exposed.database
import jp.kukv.point.infrastructure.datasource.exposed.ActiveOwnershipPointTable
import jp.kukv.point.infrastructure.datasource.exposed.OwnershipPointTable
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionNumberTable
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionTable
import jp.kukv.point.infrastructure.datasource.exposed.transaction
import org.jetbrains.exposed.sql.deleteAll

object PointRegisterRoutingTestMapper {
    private val database = database()

    fun clear() {
        transaction(database) {
            ActiveOwnershipPointTable.deleteAll()
            OwnershipPointTable.deleteAll()

            PointTransactionTable.deleteAll()
            PointTransactionNumberTable.deleteAll()
        }
    }
}
