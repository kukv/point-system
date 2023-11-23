package jp.kukv.point.infrastructure.datasource.exposed

import jp.kukv.point.domain.model.transaction.Event
import jp.kukv.point.infrastructure.datasource.exposed.PointTransactionTable.references
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object PointTransactionNumberTable : Table("point_transaction_number") {
    val transactionNumber = uuid("transaction_number")
    val buyerId = integer("buyer_id")
    val createdAt = datetime("created_at")
    val created = varchar("created", 255)
    override val primaryKey = PrimaryKey(transactionNumber)
}

object PointTransactionTable : Table("point_transaction") {
    val transactionNumber = uuid("transaction_number").references(PointTransactionNumberTable.transactionNumber)
    val transactionSource = varchar("transaction_source", 200)
    val requestedAt = datetime("requested_at")
    val point = integer("point")
    val createdAt = datetime("created_at")
    val created = varchar("created", 255)
    override val primaryKey = PrimaryKey(transactionNumber)
}

object EarnPointReservationTable : Table("earn_point_reservation") {
    val id = integer("id").autoIncrement("earn_point_reservation_id_seq")
    val transactionNumber = uuid("transaction_number").references(PointTransactionNumberTable.transactionNumber)
    val reason = varchar("reason", 200)
    val point = integer("point")
    val createdAt = datetime("created_at")
    val created = varchar("created", 255)
    override val primaryKey = PrimaryKey(id)
}

object UnreflectedEarnPointTransactionTable : Table("unreflected_earn_point_transaction") {
    val transactionNumber = uuid("transaction_number").references(PointTransactionNumberTable.transactionNumber)
    val transactionSource = varchar("transaction_source", 200)
    val point = integer("point")
    val createdAt = datetime("created_at")
    val created = varchar("created", 255)
    override val primaryKey = PrimaryKey(transactionNumber)
}

object CanceledPointTransactionTable : Table("canceled_point_transaction") {
    val transactionNumber = uuid("transaction_number").references(PointTransactionNumberTable.transactionNumber)
    val cancelTransactionNumber = uuid("cancel_transaction_number").references(PointTransactionNumberTable.transactionNumber)
    val createdAt = datetime("created_at")
    val created = varchar("created", 255)
    override val primaryKey = PrimaryKey(transactionNumber)
}

object TransactionEventTable : Table("transaction_event") {
    val event = enumerationByName<Event>("event", 10)
    override val primaryKey = PrimaryKey(event)
}

object PointTransactionEventTable : Table("point_transaction_event") {
    val transactionNumber = uuid("transaction_number").references(PointTransactionNumberTable.transactionNumber)
    val transactionEvent = enumerationByName<Event>("transaction_event", 10).references(TransactionEventTable.event)
    val createdAt = datetime("created_at")
    val created = varchar("created", 255)
    override val primaryKey = PrimaryKey(transactionNumber)
}

object ExpirationDateTable : Table("expiration_date") {
    val id = integer("id").autoIncrement("expiration_date_id_seq")
    val buyerId = integer("buyer_id")
    val expiredAt = date("expired_at")
    val createdAt = datetime("created_at")
    val created = varchar("created", 255)
    override val primaryKey = PrimaryKey(id)
}
