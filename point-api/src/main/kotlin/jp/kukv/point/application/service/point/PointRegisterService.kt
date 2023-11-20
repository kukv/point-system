package jp.kukv.point.application.service.point

import jp.kukv.point.application.repository.point.PointRegisterRepository
import org.koin.core.annotation.Single

@Single
class PointRegisterService(
    private val pointRegisterRepository: PointRegisterRepository,
) : PointRegisterRepository by pointRegisterRepository
