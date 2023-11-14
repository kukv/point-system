package jp.kukv.point.endpoint.point

import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import jp.kukv.point.application.service.point.PointRegisterService
import jp.kukv.point.extensions.ktor.receiveAndValidate
import org.koin.ktor.ext.inject

object PointRegisterController {
    fun Route.register() {
        val pointRegisterService by inject<PointRegisterService>()

        post("/v1/point/register", {
            operationId = "1.ポイント登録"
            tags = listOf("ポイント")
            summary = "ポイント登録"
            description = "ポイントを登録する"
            request {
                body<PointRegisterRequest>()
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
            val request = call.receiveAndValidate<PointRegisterRequest>()

            pointRegisterService.register(request.id, request.subTotalAmount)
            call.respond(HttpStatusCode.OK)
        }
    }
}
