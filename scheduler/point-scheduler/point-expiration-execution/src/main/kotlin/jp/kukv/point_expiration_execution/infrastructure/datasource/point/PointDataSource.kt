package jp.kukv.point_expiration_execution.infrastructure.datasource.point

import jp.kukv.point_expiration_execution.application.repository.point.PointRepository
import jp.kukv.point_expiration_execution.domain.model.point.OwnershipPoints
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PointDataSource : PointRepository {
    private val log = LoggerFactory.getLogger(PointDataSource::class.java)

    @Transactional(readOnly = true)
    override fun listAll(): OwnershipPoints {
        log.info("顧客別保有ポイント一覧取得 開始")

        val ownershipPoints =
            runCatching { PointMapper.listAll() }
                .getOrElse { exception ->
                    log.error("顧客別保有ポイント一覧取得失敗", exception)
                    throw exception
                }

        log.info("顧客別保有ポイント一覧取得 終了 {}件", ownershipPoints.size)
        return OwnershipPoints(ownershipPoints)
    }
}
