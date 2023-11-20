package jp.kukv.point.infrastructure.datasource.exposed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun <T> transaction(
    database: Database,
    block: suspend () -> T,
): T =
    runCatching {
        runBlocking {
            newSuspendedTransaction(Dispatchers.IO, database) {
                addLogger(StdOutSqlLogger)
                block()
            }
        }
    }.getOrElse {
        throw RuntimeException(it.message, it)
    }
