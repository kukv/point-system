package jp.kukv.point_expiration_execution

import jp.kukv.point_expiration_execution.application.service.point.PointExpirationService
import jp.kukv.point_expiration_execution.application.service.point.PointService
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class Application(
    private val pointService: PointService,
    private val pointExpirationService: PointExpirationService,
) : ApplicationRunner {
    private val log = LoggerFactory.getLogger(Application::class.java)

    override fun run(args: ApplicationArguments?) {
        log.info("ポイント失効バッチ 開始")

        runCatching {
            val ownershipPoints = pointService.listAll()

            val expiredOwnershipPoints = ownershipPoints.toExpiredOwnershipPoints()
            pointExpirationService.expire(expiredOwnershipPoints)
        }
            .onSuccess {
                log.info("ポイント失効バッチ 終了")
            }
            .onFailure { exception ->
                log.error("ポイント失効バッチで予期せぬエラー")
                throw exception
            }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
