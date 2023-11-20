package jp.kukv.point_expiration_execution.infrastructure.datasource.transaction

import jp.kukv.point_expiration_execution.application.repository.transaction.TransactionRegisterRepository
import jp.kukv.point_expiration_execution.domain.model.identify.Id
import jp.kukv.point_expiration_execution.domain.model.point.Point
import jp.kukv.point_expiration_execution.domain.model.transaction.TransactionNumber
import jp.kukv.point_expiration_execution.domain.model.transaction.TransactionType
import jp.kukv.point_expiration_execution.infrastructure.datasource.Audit
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class TransactionRegisterDataSource : TransactionRegisterRepository {
    @Transactional
    override fun register(
        id: Id,
        point: Point,
    ) {
        val audit = Audit.create(TransactionRegisterDataSource::class)
        val transactionNumber = TransactionNumber.create()

        TransactionRegisterMapper.registerNumber(id, transactionNumber, audit)
        TransactionRegisterMapper.registerTransaction(transactionNumber, TransactionType.失効, point, audit)
    }
}
