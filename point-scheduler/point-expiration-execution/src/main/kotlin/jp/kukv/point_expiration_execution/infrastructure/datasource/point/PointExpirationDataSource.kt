package jp.kukv.point_expiration_execution.infrastructure.datasource.point

import jp.kukv.point_expiration_execution.application.repository.point.PointExpirationRepository
import jp.kukv.point_expiration_execution.application.repository.transaction.TransactionRegisterRepository
import jp.kukv.point_expiration_execution.domain.model.point.ExpiredOwnershipPoints
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PointExpirationDataSource(private val transactionRegisterRepository: TransactionRegisterRepository) : PointExpirationRepository {
    private val log = LoggerFactory.getLogger(PointExpirationDataSource::class.java)

    @Transactional
    override fun expire(expiredOwnershipPoints: ExpiredOwnershipPoints) {
        log.info("ポイント失効 開始")
        for (id in expiredOwnershipPoints.asKeys()) {
            PointExpirationMapper.removeActive(id)

            val ownershipPoint = expiredOwnershipPoints.find(id)
            transactionRegisterRepository.register(id, ownershipPoint.point)
        }

        log.info("ポイント失効 終了 {}件", expiredOwnershipPoints.size())
    }
}
