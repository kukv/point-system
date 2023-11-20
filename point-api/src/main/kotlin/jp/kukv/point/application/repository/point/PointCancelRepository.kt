package jp.kukv.point.application.repository.point

import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.transaction.TransactionNumber

interface PointCancelRepository {
    fun cancel(
        id: Id,
        transactionNumber: TransactionNumber,
    )
}
