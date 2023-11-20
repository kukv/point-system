package jp.kukv.point_expiration_execution.application.service.point

import jp.kukv.point_expiration_execution.application.repository.point.PointExpirationRepository
import org.springframework.stereotype.Service

@Service
class PointExpirationService(
    private val pointExpirationRepository: PointExpirationRepository,
) : PointExpirationRepository by pointExpirationRepository
