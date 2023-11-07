package jp.kukv.point_expiration_execution.infrastructure.datasource.exposed

import jp.kukv.point_expiration_execution.domain.model.transaction.TransactionType
import jp.kukv.point_expiration_execution.infrastructure.datasource.exposed.PointTransactionTable.references
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object OwnershipPointTable : Table("ownership_point") {
    val id = integer("id").autoIncrement("ownership_point_id_seq")
    val dinerId = integer("diner_id")
    val point = integer("point")
    val expiredAt = date("expired_at")
    val createdAt = datetime("created_at")
    val audit = varchar("audit", 255)
    override val primaryKey = PrimaryKey(id)
}

object ActiveOwnershipPointTable : Table("active_ownership_point") {
    val ownershipPointId = integer("ownership_point_id").references(OwnershipPointTable.id)
}

object TransactionTypeTable : Table("transaction_type") {
    val type = enumerationByName<TransactionType>("type", 10)
    override val primaryKey = PrimaryKey(type)
}

object PointTransactionNumberTable : Table("point_transaction_number") {
    val transactionNumber = uuid("transaction_number")
    val dinerId = integer("diner_id")
    val createdAt = datetime("created_at")
    val audit = varchar("audit", 255)
    override val primaryKey = PrimaryKey(transactionNumber)
}

object PointTransactionTable : Table("point_transaction") {
    val transactionNumber = uuid("transaction_number").references(PointTransactionNumberTable.transactionNumber)
    val transactionType = enumerationByName<TransactionType>("transaction_type", 10).references(TransactionTypeTable.type)
    val point = integer("point")
    val createdAt = datetime("created_at")
    val audit = varchar("audit", 255)
    override val primaryKey = PrimaryKey(transactionNumber)
}
