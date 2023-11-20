package jp.kukv.point.endpoint.transaction

import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import jp.kukv.point._extensions.kotlinx.now
import jp.kukv.point.application.service.transaction.TransactionService
import jp.kukv.point.domain.model.identify.Id
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.transaction.Transaction
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.domain.model.transaction.TransactionTime
import jp.kukv.point.domain.model.transaction.TransactionType
import jp.kukv.point.domain.model.transaction.Transactions
import kotlinx.datetime.LocalDateTime
import org.koin.ktor.ext.inject

object TransactionController {
    fun Route.history() {
        val transactionService by inject<TransactionService>()

        get("/v1/point/history", {
            operationId = "1.ポイント取引履歴取得"
            tags = listOf("取引履歴")
            summary = "ポイント取引履歴取得"
            description = "ポイント取引履歴を取得する"
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
                    body<Transactions> {
                        example(
                            "ポイントの利用履歴が存在しない",
                            Transactions(emptyList()),
                        )
                        example(
                            "ポイントの利用履歴あり",
                            Transactions(
                                listOf(
                                    Transaction(
                                        TransactionNumber.create(),
                                        Point(10),
                                        TransactionType.利用,
                                        TransactionTime(LocalDateTime.now()),
                                    ),
                                    Transaction(
                                        TransactionNumber.create(),
                                        Point(10),
                                        TransactionType.獲得,
                                        TransactionTime(LocalDateTime.now()),
                                    ),
                                    Transaction(
                                        TransactionNumber.create(),
                                        Point(20),
                                        TransactionType.失効,
                                        TransactionTime(LocalDateTime.now()),
                                    ),
                                ),
                            ),
                        )
                    }
                }
                HttpStatusCode.BadRequest to {
                    description = "リクエストが不正"
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

            call.respond(transactionService.listOf(id))
        }
    }
}
