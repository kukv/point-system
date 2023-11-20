package jp.kukv.point.application.repository.point

import jp.kukv.point.domain.model.check.SubTotalAmount
import jp.kukv.point.domain.model.identify.Id

interface PointRegisterRepository {
    fun register(
        id: Id,
        subTotalAmount: SubTotalAmount,
    )
}
