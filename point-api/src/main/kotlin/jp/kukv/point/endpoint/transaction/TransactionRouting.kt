package jp.kukv.point.endpoint.transaction

import io.ktor.server.routing.Route
import jp.kukv.point.endpoint.transaction.TransactionController.history

fun Route.transactionRouting() {
    history()
}
