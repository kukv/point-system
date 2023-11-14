package jp.kukv.point.endpoint.point

import io.ktor.client.call.body
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import jp.kukv.point._extensions.kotlinx.now
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
import jp.kukv.point.infrastructure.datasource.point.PointUseRoutingTestMapper
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.UUID

@DisplayName("ルーティングテスト: /point/use")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PointUseRoutingTest {
    @BeforeAll
    fun setup() {
        PointUseRoutingTestMapper.register()
        mockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
        mockkStatic(UUID::class)
    }

    @AfterAll
    fun clear() {
        PointUseRoutingTestMapper.clear()
        unmockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
        unmockkStatic(UUID::class)
    }

    @Test
    @DisplayName("ポイント利用できる")
    fun `ポイント利用できる`() =
        testApplication {
            every { UUID.randomUUID() } returns UUID.fromString("b00b96a8-4345-4515-b59f-cb31928f2c67")
            every { LocalDateTime.now() } returns LocalDateTime.parse("2023-11-03T00:00:00")

            val response =
                post("/v1/point/use") {
                    setBody(PointUseDummyRequest(1, 10))
                }
            Assertions.assertEquals(HttpStatusCode.OK, response.status)

            val actualOwnershipPoint = get("/v1/point") { url { parameters["id"] = "1" } }.body<OwnershipPoint>()
            val expectedOwnershipPoint = OwnershipPoint(Point(90), ExpirationPeriod(LocalDate.parse("2024-11-01")))
            Assertions.assertEquals(expectedOwnershipPoint.toString(), actualOwnershipPoint.toString())

            val actualPointUsageHistories = get("/v1/point/history") { url { parameters["id"] = "1" } }.body<Transactions>()
            val expectedPointUsageHistories =
                Transactions(
                    listOf(
                        Transaction(
                            TransactionNumber(UUID.fromString("c1e0b604-cef1-4e5d-94b2-704ca17f261b")),
                            Point(100),
                            TransactionType.獲得,
                            TransactionTime(LocalDateTime.parse("2023-11-01T00:00:00")),
                        ),
                        Transaction(
                            TransactionNumber(UUID.fromString("b00b96a8-4345-4515-b59f-cb31928f2c67")),
                            Point(10),
                            TransactionType.利用,
                            TransactionTime(LocalDateTime.parse("2023-11-03T00:00:00")),
                        ),
                    ),
                )

            Assertions.assertEquals(expectedPointUsageHistories.toString(), actualPointUsageHistories.toString())
        }

    @Test
    @DisplayName("存在しないIDの場合保有ポイント情報が存在しない不正エラーとなる")
    fun `存在しないIDの場合保有ポイント情報が存在しない不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/use") {
                    setBody(PointUseDummyRequest(100, 10))
                }
            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
        }

    @Test
    @DisplayName("ポイントが不足している場合リクエスト不正エラーとなる")
    fun `ポイントが不足している場合リクエスト不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/use") {
                    setBody(PointUseDummyRequest(1, 1000))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("ポイントの有効期限が切れている場合リクエスト不正エラーとなる")
    fun `ポイントの有効期限が切れている場合リクエスト不正エラーとなる`() =
        testApplication {
            every { LocalDateTime.now() } returns LocalDateTime.parse("2025-11-01T00:00:00")

            val response =
                post("/v1/point/use") {
                    setBody(PointUseDummyRequest(1, 90))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("リクエストボディが空の場合リクエスト不正エラーとなる")
    fun `リクエストボディが空の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/use") {
                    setBody(PointUseDummyRequest(null, null))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("リクエストボディのIDが空の場合リクエスト不正エラーとなる")
    fun `リクエストボディのIDが空の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/use") {
                    setBody(PointUseDummyRequest(1, null))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("リクエストボディのポイントが空の場合リクエスト不正エラーとなる")
    fun `リクエストボディのポイントが空の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/use") {
                    setBody(PointUseDummyRequest(null, 10))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }
}
