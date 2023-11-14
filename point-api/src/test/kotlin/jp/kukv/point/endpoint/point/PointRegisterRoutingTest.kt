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
import jp.kukv.point.infrastructure.datasource.point.PointRegisterRoutingTestMapper
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
class PointRegisterRoutingTest {
    @BeforeAll
    fun setup() {
        mockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
        mockkStatic(UUID::class)
    }

    @AfterAll
    fun clear() {
        PointRegisterRoutingTestMapper.clear()
        unmockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
        unmockkStatic(UUID::class)
    }

    @Test
    @DisplayName("存在しないIDに対してポイント登録できる")
    fun `存在しないIDに対してポイント登録できる`() =
        testApplication {
            every { UUID.randomUUID() } returns UUID.fromString("b00b96a8-4345-4515-b59f-cb31928f2c67")
            every { LocalDateTime.now() } returns LocalDateTime.parse("2023-11-03T00:00:00")

            val response =
                post("/v1/point/register") {
                    setBody(PointRegisterDummyRequest(1, 1000))
                }
            Assertions.assertEquals(HttpStatusCode.OK, response.status)

            val actualOwnershipPoint = get("/v1/point") { url { parameters["id"] = "1" } }.body<OwnershipPoint>()
            val expectedOwnershipPoint = OwnershipPoint(Point(10), ExpirationPeriod(LocalDate.parse("2024-11-03")))
            Assertions.assertEquals(expectedOwnershipPoint.toString(), actualOwnershipPoint.toString())

            val actualTransactions = get("/v1/point/history") { url { parameters["id"] = "1" } }.body<Transactions>()
            val expectedTransactions =
                Transactions(
                    listOf(
                        Transaction(
                            TransactionNumber(UUID.fromString("b00b96a8-4345-4515-b59f-cb31928f2c67")),
                            Point(10),
                            TransactionType.獲得,
                            TransactionTime(LocalDateTime.parse("2023-11-03T00:00:00")),
                        ),
                    ),
                )
            Assertions.assertEquals(expectedTransactions.toString(), actualTransactions.toString())
        }

    @Test
    @DisplayName("存在するIDに対してポイント登録ができる")
    fun `存在するIDに対してポイント登録ができる`() =
        testApplication {
            every { UUID.randomUUID() } returns UUID.fromString("9e93c3bd-ccd9-4a61-a873-877d8678e74f")
            every { LocalDateTime.now() } returns LocalDateTime.parse("2023-11-04T00:00:00")

            val response =
                post("/v1/point/register") {
                    setBody(PointRegisterDummyRequest(1, 1000))
                }
            Assertions.assertEquals(HttpStatusCode.OK, response.status)

            val actualOwnershipPoint = get("/v1/point") { url { parameters["id"] = "1" } }.body<OwnershipPoint>()
            val expectedOwnershipPoint = OwnershipPoint(Point(20), ExpirationPeriod(LocalDate.parse("2024-11-04")))
            Assertions.assertEquals(expectedOwnershipPoint.toString(), actualOwnershipPoint.toString())

            val actualPointUsageHistories = get("/v1/point/history") { url { parameters["id"] = "1" } }.body<Transactions>()
            val expectedPointUsageHistories =
                Transactions(
                    listOf(
                        Transaction(
                            TransactionNumber(UUID.fromString("b00b96a8-4345-4515-b59f-cb31928f2c67")),
                            Point(10),
                            TransactionType.獲得,
                            TransactionTime(LocalDateTime.parse("2023-11-03T00:00:00")),
                        ),
                        Transaction(
                            TransactionNumber(UUID.fromString("9e93c3bd-ccd9-4a61-a873-877d8678e74f")),
                            Point(10),
                            TransactionType.獲得,
                            TransactionTime(LocalDateTime.parse("2023-11-04T00:00:00")),
                        ),
                    ),
                )
            Assertions.assertEquals(expectedPointUsageHistories.toString(), actualPointUsageHistories.toString())
        }

    @Test
    @DisplayName("リクエストボディが空の場合リクエスト不正エラーとなる")
    fun `リクエストボディが空の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/register") {
                    setBody(PointRegisterDummyRequest(null, null))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("リクエストボディのIDが空の場合リクエスト不正エラーとなる")
    fun `リクエストボディのIDが空の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/register") {
                    setBody(PointRegisterDummyRequest(null, 1000))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("リクエストボディの金額が空の場合リクエスト不正エラーとなる")
    fun `リクエストボディの金額が空の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response =
                post("/v1/point/register") {
                    setBody(PointRegisterDummyRequest(2, null))
                }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }
}
