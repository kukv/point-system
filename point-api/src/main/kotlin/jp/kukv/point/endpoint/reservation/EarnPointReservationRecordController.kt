package jp.kukv.point.endpoint.reservation

import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import jp.kukv.point._extensions.ktor.receiveAndValidate
import jp.kukv.point.applicarion.service.reservation.EarnPointReservationRecordService
import org.koin.ktor.ext.inject

fun Route.reservation() {
    val earnPointReservationRecordService by inject<EarnPointReservationRecordService>()

    post("/v1/point/reservation", {
        operationId = "1.決済金額に応じたポイントの獲得予約を記録"
        tags = listOf("ポイント")
        summary = "決済金額に応じたポイントの獲得予約を記録"
        description = "決済金額に応じたポイントの獲得予約を記録"
        request {
            body<EarnPointReservationRecordRequest>()
        }
        response {
            HttpStatusCode.OK to {
                description = HttpStatusCode.OK.description
                body<EarnPointReservationRecordResponse>()
            }
            HttpStatusCode.BadRequest to {
                description = "リクエストが不正"
            }
            HttpStatusCode.InternalServerError to {
                description = "サーバー起因エラー"
            }
        }
    }) {
        val request = call.receiveAndValidate<EarnPointReservationRecordRequest>()

        val reservationNumber = earnPointReservationRecordService.reservation(request.id, request.reason, request.paymentAmount)
        val response = EarnPointReservationRecordResponse(reservationNumber)
        call.respond(HttpStatusCode.OK, response)
    }
}
