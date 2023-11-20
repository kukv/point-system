package jp.kukv.point._configuration.koin

import jp.kukv.environment.EnvironmentComponent
import jp.kukv.point._configuration.exposed.PointConfiguration
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("jp.kukv.point")
class InjectConfiguration : EnvironmentComponent {
    @Single
    fun pointDataSource(): Database {
        val dataSource = PointConfiguration()
        return dataSource.toDatabase()
    }
}
