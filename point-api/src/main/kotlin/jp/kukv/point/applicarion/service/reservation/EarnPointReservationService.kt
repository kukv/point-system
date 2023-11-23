package jp.kukv.point.applicarion.service.reservation

import jp.kukv.point.applicarion.repository.reservation.EarnPointReservationRepository
import org.koin.core.annotation.Single

@Single
class EarnPointReservationService(
    private val earnPointReservationRepository: EarnPointReservationRepository,
) : EarnPointReservationRepository by earnPointReservationRepository
