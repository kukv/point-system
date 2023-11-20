package jp.kukv.point.application.repository.point

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.Point

interface PointUseRepository {
    fun use(
        id: Id,
        point: Point,
    )
}
