package jp.kukv.point.application.service.point

import jp.kukv.point.application.repository.point.PointUseRepository
import org.koin.core.annotation.Single

@Single
class PointUseService(private val pointUseRepository: PointUseRepository) : PointUseRepository by pointUseRepository
