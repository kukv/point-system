package jp.kukv.point.application.repository.point

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.OwnershipPoint

interface PointRepository {
    fun findBy(id: Id): OwnershipPoint
}
