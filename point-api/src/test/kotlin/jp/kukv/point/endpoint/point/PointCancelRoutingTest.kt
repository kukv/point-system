package jp.kukv.point.endpoint.point

import io.ktor.client.call.body
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import jp.kukv.point.domain.model.point.ExpirationPeriod
import jp.kukv.point.domain.model.point.OwnershipPoint
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.transaction.Transaction
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.domain.model.transaction.TransactionTime
import jp.kukv.point.domain.model.transaction.TransactionType
import jp.kukv.point.domain.model.transaction.Transactions
import jp.kukv.point.endpoint.get
import jp.kukv.point.endpoint.post
import jp.kukv.point.extensions.kotlinx.now
import jp.kukv.point.infrastructure.datasource.point.PointCancelRoutingTestMapper
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.UUID

@DisplayName("ルーティングテスト: /point/register")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PointCancelRoutingTest {
    @BeforeAll
    fun setup() {
        PointCancelRoutingTestMapper.register()
        mockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
        mockkStatic(UUID::class)
    }

    @AfterAll
    fun clear() {
        PointCancelRoutingTestMapper.clear()
        unmockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
        unmockkStatic(UUID::class)
    }

    @Test
    @DisplayName("ポイント利用のキャンセルができる")
    fun `ポイント利用のキャンセルができる`() =
        testApplication {
            every { UUID.randomUUID() } returns UUID.fromString("fc84f2c0-f57c-4ddd-840a-b97d55eda4a9")
            every { LocalDateTime.now() } returns LocalDateTime.parse("2023-11-20T00:00:00")

            val response =
                post("/v1/point/cancel") {
                    setBody(PointCancelDummyRequest(1, UUID.fromString("424f2509-640d-44d3-a983-045fcf4665e8")))
                }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)

            val actualOwnershipPoint = get("/v1/point") { url { parameters["id"] = "1" } }.body<OwnershipPoint>()
            val expectedOwnershipPoint = OwnershipPoint(Point(100), ExpirationPeriod(LocalDate.parse("2024-11-16")))
            Assertions.assertEquals(expectedOwnershipPoint.toString(), actualOwnershipPoint.toString())

            val actualPointUsageHistories = get("/v1/point/history") { url { parameters["id"] = "1" } }.body<Transactions>()
            val expectedPointUsageHistories =
                Transactions(
                    listOf(
                        Transaction(
                            TransactionNumber(UUID.fromString("d5951ea5-9060-4331-9c45-b3a043730ced")),
                            Point(100),
                            TransactionType.獲得,
                            TransactionTime(LocalDateTime.parse("2023-11-16T00:00:00")),
                        ),
                        Transaction(
                            TransactionNumber(UUID.fromString("424f2509-640d-44d3-a983-045fcf4665e8")),
                            Point(10),
                            TransactionType.利用,
                            TransactionTime(LocalDateTime.parse("2023-11-18T00:00:00")),
                        ),
                        Transaction(
                            TransactionNumber(UUID.fromString("fc84f2c0-f57c-4ddd-840a-b97d55eda4a9")),
                            Point(10),
                            TransactionType.キャンセル,
                            TransactionTime(LocalDateTime.parse("2023-11-20T00:00:00")),
                        ),
                    ),
                )

            Assertions.assertEquals(expectedPointUsageHistories.toString(), actualPointUsageHistories.toString())
        }

    @Test
    @DisplayName("ポイントの利用履歴が存在しない場合ポイント利用のキャンセルができない")
    fun `ポイントの利用履歴が存在しない場合ポイント利用のキャンセルができない`() =
        testApplication {
            val response =
                post("/v1/point/cancel") {
                    setBody(PointCancelDummyRequest(99, UUID.randomUUID()))
                }

            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
        }

    @Test
    @DisplayName("ポイントの利用履歴が1ヶ月以上前の場合ポイント利用のキャンセルができない")
    fun `ポイントの利用履歴が1ヶ月以上前の場合ポイント利用のキャンセルができない`() =
        testApplication {
            every { LocalDateTime.now() } returns LocalDateTime.parse("2023-12-30T00:00:00")

            val response =
                post("/v1/point/cancel") {
                    setBody(PointCancelDummyRequest(2, UUID.fromString("f91d568e-2723-4c03-b807-ea35d98b7ec3")))
                }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("ポイントの利用履歴の種別が失効またはキャンセルの場合ポイント利用のキャンセルができない")
    fun `ポイントの利用履歴の種別が失効またはキャンセルの場合ポイント利用のキャンセルができない`() =
        testApplication {
            val response =
                post("/v1/point/cancel") {
                    setBody(PointCancelDummyRequest(1, UUID.fromString("fc84f2c0-f57c-4ddd-840a-b97d55eda4a9")))
                }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("保有ポイントの有効期限が切れている場合ポイント利用のキャンセルができない")
    fun `保有ポイントの有効期限が切れている場合ポイント利用のキャンセルができない`() =
        testApplication {
            every { LocalDateTime.now() } returns LocalDateTime.parse("2024-12-17T00:00:00")

            val response =
                post("/v1/point/cancel") {
                    setBody(PointCancelDummyRequest(3, UUID.fromString("8d4e7075-71c6-4ae8-b15d-267ff51dd244")))
                }

            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("リクエスト項目が空の場合リクエスト不正エラーとなる")
    fun `リクエスト項目が空の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/cancel") {
                    setBody(PointCancelDummyRequest(null, null))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("リクエスト項目にIDが存在しない場合リクエスト不正エラーとなる")
    fun `リクエスト項目にIDが存在しない場合リクエスト不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/cancel") {
                    setBody(PointCancelDummyRequest(null, UUID.randomUUID()))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("リクエスト項目に取引番号が存在しない場合リクエスト不正エラーとなる")
    fun `リクエスト項目に取引番号が存在しない場合リクエスト不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/cancel") {
                    setBody(PointCancelDummyRequest(2, null))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }
}
