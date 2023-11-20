package jp.kukv.point_expiration_execution.application.service.point

import jp.kukv.point_expiration_execution.application.repository.point.PointRepository
import org.springframework.stereotype.Service

@Service
class PointService(private val pointRepository: PointRepository) : PointRepository by pointRepository
