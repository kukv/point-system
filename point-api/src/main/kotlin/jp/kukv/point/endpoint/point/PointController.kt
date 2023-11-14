package jp.kukv.point.endpoint.point

import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import jp.kukv.point.application.service.point.PointService
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.ExpirationPeriod
import jp.kukv.point.domain.model.point.OwnershipPoint
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.extensions.kotlinx.now
import kotlinx.datetime.LocalDate
import org.koin.ktor.ext.inject
import kotlin.IllegalArgumentException

object PointController {
    fun Route.findBy() {
        val pointService by inject<PointService>()

        get("/v1/point", {
            operationId = "1.保有ポイント取得"
            tags = listOf("ポイント")
            summary = "保有ポイント取得"
            description = "保有ポイントを取得する"
            request {
                queryParameter<Int>("id") {
                    description = "ID"
                    example = 1
                    required = true
                    allowEmptyValue = false
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = HttpStatusCode.OK.description
                    body<OwnershipPoint> {
                        example(
                            "保有ポイント",
                            OwnershipPoint(Point(100), ExpirationPeriod(LocalDate.now())),
                        )
                    }
                }
                HttpStatusCode.BadRequest to {
                    description = "リクエストが不正"
                }
                HttpStatusCode.NotFound to {
                    description = "保有ポイントが存在しない"
                }
                HttpStatusCode.InternalServerError to {
                    description = "サーバー起因エラー"
                }
            }
        }) {
            val temporary = call.request.queryParameters["id"] ?: throw IllegalArgumentException("Idは必須")
            val id = Id.create(temporary)

            val validator = Id.validator
            val violation = validator.validate(id)
            if (!violation.isValid) {
                throw IllegalArgumentException(violation.toString())
            }

            call.respond(pointService.findBy(id))
        }
    }
}
