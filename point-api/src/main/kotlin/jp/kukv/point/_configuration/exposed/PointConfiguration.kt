package jp.kukv.point._configuration.exposed

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jp.kukv.environment.EnvironmentComponent
import jp.kukv.environment.inject
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class PointConfiguration : EnvironmentComponent {
    private val jdbcUrl by inject<String>("external.dataSource.point.jdbcUrl")
    private val driver by inject<String>("external.dataSource.point.driverClassName")
    private val userName by inject<String>("external.dataSource.point.userName")
    private val password by inject<String>("external.dataSource.point.password")
    private val maximumPoolSize by inject<Int>("external.dataSource.point.maximumPoolSize")
    private val autoCommit by inject<Boolean>("external.dataSource.point.autoCommit")
    private val transactionIsolation by inject<String>("external.dataSource.point.transactionIsolation")

    @Single
    fun database(): Database {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = jdbcUrl
        hikariConfig.driverClassName = driver
        hikariConfig.username = userName
        hikariConfig.password = password
        hikariConfig.maximumPoolSize = maximumPoolSize
        hikariConfig.isAutoCommit = autoCommit
        hikariConfig.transactionIsolation = transactionIsolation

        val dataSource = HikariDataSource(hikariConfig)

        return Database.connect(
            datasource = dataSource,
            databaseConfig = DatabaseConfig.invoke { useNestedTransactions = true },
        )
    }
}
