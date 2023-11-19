package jp.kukv.point.application.service.point

import jp.kukv.point.application.repository.point.PointCancelRepository
import org.koin.core.annotation.Single

@Single
class PointCancelService(
    private val pointCancelRepository: PointCancelRepository,
) : PointCancelRepository by pointCancelRepository
