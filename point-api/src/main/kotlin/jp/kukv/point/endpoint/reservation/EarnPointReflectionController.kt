package jp.kukv.point.endpoint.reservation

import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import jp.kukv.point._extensions.ktor.receiveAndValidate
import jp.kukv.point.applicarion.service.reservation.EarnPointReservationService
import jp.kukv.point.applicarion.service.transaction.PointTransactionRecordService
import org.koin.ktor.ext.inject

fun Route.reflection() {
    val earnPointReservationService by inject<EarnPointReservationService>()
    val pointTransactionRecordService by inject<PointTransactionRecordService>()

    post("/v1/point/reflection", {
        operationId = "1.ポイント獲得予約を反映する"
        tags = listOf("ポイント")
        summary = "ポイント獲得予約を反映する"
        description = "ポイント獲得予約を反映する"
        request {
            body<EarnPointReflectionRequest>()
        }
        response {
            HttpStatusCode.OK to {
                description = HttpStatusCode.OK.description
            }
            HttpStatusCode.BadRequest to {
                description = "リクエストが不正"
            }
            HttpStatusCode.InternalServerError to {
                description = "サーバー起因エラー"
            }
        }
    }) {
        val request = call.receiveAndValidate<EarnPointReflectionRequest>()

        val earnPointReservation = earnPointReservationService.findBy(request.reservationNumber)
        pointTransactionRecordService.record(earnPointReservation.toTransaction())

        call.respond(HttpStatusCode.OK)
    }
}
