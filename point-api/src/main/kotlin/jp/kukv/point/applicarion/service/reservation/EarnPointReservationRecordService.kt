package jp.kukv.point.applicarion.service.reservation

import jp.kukv.point.applicarion.repository.reservation.EarnPointReservationRecordRepository
import org.koin.core.annotation.Single

/**
 * 決済金額に応じたポイントの獲得予約を記録するサービス
 */
@Single
class EarnPointReservationRecordService(
    private val earnPointReservationRecordRepository: EarnPointReservationRecordRepository,
) : EarnPointReservationRecordRepository by earnPointReservationRecordRepository
