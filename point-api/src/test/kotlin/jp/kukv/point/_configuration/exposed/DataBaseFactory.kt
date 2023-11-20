package jp.kukv.point._configuration.exposed

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ConfigLoader
import io.ktor.server.config.ConfigLoader.Companion.load
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig

fun database(): Database {
    val config = ConfigLoader.load()

    val hikariConfig = HikariConfig()
    hikariConfig.jdbcUrl = config.propertyOrNull("external.dataSource.point.jdbcUrl")!!.getString()
    hikariConfig.driverClassName = config.propertyOrNull("external.dataSource.point.driverClassName")!!.getString()
    hikariConfig.username = config.propertyOrNull("external.dataSource.point.userName")!!.getString()
    hikariConfig.password = config.propertyOrNull("external.dataSource.point.password")!!.getString()

    val dataSource = HikariDataSource(hikariConfig)

    return Database.connect(
        datasource = dataSource,
        databaseConfig = DatabaseConfig.invoke { useNestedTransactions = true },
    )
}
