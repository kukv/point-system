package jp.kukv.point_expiration_execution.application.repository.point

import jp.kukv.point_expiration_execution.domain.model.point.OwnershipPoints

interface PointRepository {
    fun listAll(): OwnershipPoints
}
