package jp.kukv.point_expiration_execution.application.repository.point

import jp.kukv.point_expiration_execution.domain.model.point.ExpiredOwnershipPoints

interface PointExpirationRepository {
    fun expire(expiredOwnershipPoints: ExpiredOwnershipPoints)
}
