package jp.kukv.point.application.service.point

import jp.kukv.point.application.repository.point.PointRepository
import org.koin.core.annotation.Single

@Single
class PointService(private val pointRepository: PointRepository) : PointRepository by pointRepository
