package jp.kukv.point.endpoint.point

import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import jp.kukv.point._extensions.ktor.receiveAndValidate
import jp.kukv.point.application.service.point.PointCancelService
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.transaction.TransactionNumber
import org.koin.ktor.ext.inject

object PointCancelController {
    fun Route.cancel() {
        val pointCancelService by inject<PointCancelService>()

        post("/v1/point/cancel", {
            operationId = "1.ポイント利用キャンセル"
            tags = listOf("ポイント")
            summary = "ポイント利用キャンセル"
            description = "ポイント利用のキャンセル"
            request {
                body<PointCancelRequest> {
                    example(
                        "リクエスト",
                        PointCancelRequest(Id(1), TransactionNumber.create()),
                    )
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = HttpStatusCode.OK.description
                }
                HttpStatusCode.BadRequest to {
                    description = "リクエストが不正"
                }
                HttpStatusCode.NotFound to {
                    description = "ポイントの利用履歴が存在しない"
                }
                HttpStatusCode.InternalServerError to {
                    description = "サーバー起因エラー"
                }
            }
        }) {
            val request = call.receiveAndValidate<PointCancelRequest>()
            pointCancelService.cancel(request.id, request.transactionNumber)

            call.respond(HttpStatusCode.OK)
        }
    }
}
