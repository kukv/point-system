package jp.kukv.point.applicarion.service.transaction

import jp.kukv.point.applicarion.repository.transaction.PointTransactionRecordRepository
import org.koin.core.annotation.Single

@Single
class PointTransactionRecordService(
    private val pointTransactionRecordRepository: PointTransactionRecordRepository,
) : PointTransactionRecordRepository by pointTransactionRecordRepository
