package jp.kukv.point.application.repository.transaction

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.transaction.TransactionType

interface TransactionRegisterRepository {
    fun register(
        id: Id,
        transactionType: TransactionType,
        point: Point,
    )
}
