package jp.kukv.point.endpoint.transaction

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.domain.model.transaction.Transaction
import jp.kukv.point.domain.model.transaction.TransactionNumber
import jp.kukv.point.domain.model.transaction.TransactionTime
import jp.kukv.point.domain.model.transaction.TransactionType
import jp.kukv.point.domain.model.transaction.Transactions
import jp.kukv.point.endpoint.get
import jp.kukv.point.infrastructure.datasource.transaction.TransactionRoutingTestMapper
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
class TransactionRoutingTest {
    @BeforeAll
    fun setup() {
        TransactionRoutingTestMapper.register()
    }

    @AfterAll
    fun clear() {
        TransactionRoutingTestMapper.clear()
    }

    @Test
    @DisplayName("ポイント利用履歴一覧が取得できる")
    fun `ポイント利用履歴一覧が取得できる`() =
        testApplication {
            val response = get("/v1/point/history") { url { parameters["id"] = "1" } }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)

            val actual = response.body<Transactions>()
            val expected =
                Transactions(
                    listOf(
                        Transaction(
                            TransactionNumber(UUID.fromString("d9a12a38-3d46-4862-a0a2-dee467028bdf")),
                            Point(100),
                            TransactionType.獲得,
                            TransactionTime(LocalDateTime.parse("2023-11-01T00:00:00")),
                        ),
                        Transaction(
                            TransactionNumber(UUID.fromString("afaf72ac-7427-4538-8ea7-3dd9fddbb9c1")),
                            Point(50),
                            TransactionType.利用,
                            TransactionTime(LocalDateTime.parse("2023-11-02T00:00:00")),
                        ),
                        Transaction(
                            TransactionNumber(UUID.fromString("465a55d2-a143-4cdd-80d4-3c2cd7bc336c")),
                            Point(50),
                            TransactionType.失効,
                            TransactionTime(LocalDateTime.parse("2023-11-03T00:00:00")),
                        ),
                    ),
                )
            Assertions.assertEquals(expected.toString(), actual.toString())
        }

    @Test
    @DisplayName("存在しないIDの場合空のリストが取得できる")
    fun `存在しないIDの場合空のリストが取得できる`() =
        testApplication {
            val response = get("/v1/point/history") { url { parameters["id"] = "2" } }
            Assertions.assertEquals(HttpStatusCode.OK, response.status)

            val actual = response.body<Transactions>()
            val expected = Transactions(emptyList())
            Assertions.assertEquals(expected.toString(), actual.toString())
        }

    @Test
    @DisplayName("IDが空の場合リクエスト不正エラーとなる")
    fun `IDが空の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response = get("/v1/point/history") {}
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("IDが0の場合リクエスト不正エラーとなる")
    fun `IDが0の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response = get("/v1/point/history") { url { parameters["id"] = "0" } }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("IDが2_000_000_001の場合リクエスト不正エラーとなる")
    fun `IDが2_000_000_001の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response = get("/v1/point/history") { url { parameters["id"] = "2000000001" } }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }
}
