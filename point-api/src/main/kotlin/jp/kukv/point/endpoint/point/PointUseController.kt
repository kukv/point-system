package jp.kukv.point.endpoint.point

import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import jp.kukv.point.application.service.point.PointUseService
import jp.kukv.point.extensions.ktor.receiveAndValidate
import org.koin.ktor.ext.inject

object PointUseController {
    fun Route.use() {
        val pointUseService by inject<PointUseService>()

        post("/v1/point/use", {
            operationId = "1.ポイント利用"
            tags = listOf("ポイント")
            summary = "ポイント利用"
            description = "ポイントを利用する"
            request {
                body<PointUseRequest>()
            }
            response {
                HttpStatusCode.OK to {
                    description = HttpStatusCode.OK.description
                }
                HttpStatusCode.BadRequest to {
                    description = "リクエストが不正"
                }
                HttpStatusCode.NotFound to {
                    description = "保有ポイント情報が存在しない"
                }
                HttpStatusCode.InternalServerError to {
                    description = "サーバー起因エラー"
                }
            }
        }) {
            val request = call.receiveAndValidate<PointUseRequest>()

            pointUseService.use(request.id, request.point)
            call.respond(HttpStatusCode.OK)
        }
    }
}
