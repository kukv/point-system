package jp.kukv.point_expiration_execution.application.repository.transaction

import jp.kukv.point_expiration_execution.domain.model.identify.Id
import jp.kukv.point_expiration_execution.domain.model.point.Point

interface TransactionRegisterRepository {
    fun register(
        id: Id,
        point: Point,
    )
}
