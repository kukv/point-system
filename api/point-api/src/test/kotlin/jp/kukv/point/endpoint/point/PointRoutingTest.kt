package jp.kukv.point.endpoint.point

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import jp.kukv.point.domain.model.point.ExpirationPeriod
import jp.kukv.point.domain.model.point.OwnershipPoint
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.endpoint.get
import jp.kukv.point.infrastructure.datasource.point.PointRoutingTestMapper
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@DisplayName("ルーティングテスト: /point")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PointRoutingTest {
    @BeforeAll
    fun setup() {
        PointRoutingTestMapper.register()
    }

    @AfterAll
    fun clear() {
        PointRoutingTestMapper.clear()
    }

    @Test
    @DisplayName("ポイント情報が取得できる")
    fun `ポイント情報が取得できる`() =
        testApplication {
            val response = get("/v1/point") { url { parameters["id"] = "1" } }

            Assertions.assertEquals(HttpStatusCode.OK, response.status)

            val actual = response.body<OwnershipPoint>()
            val expected = OwnershipPoint(Point(100), ExpirationPeriod(LocalDate.parse("2023-11-01")))
            Assertions.assertEquals(expected.toString(), actual.toString())
        }

    @Test
    @DisplayName("IDが空の場合リクエスト不正エラーとなる")
    fun `IDが空の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response = get("/v1/point") {}
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("IDが0の場合リクエスト不正エラーとなる")
    fun `IDが0の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response = get("/v1/point") { url { parameters["id"] = "0" } }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("IDが2_000_000_001の場合リクエスト不正エラーとなる")
    fun `IDが2_000_000_001の場合リクエスト不正エラーとなる`() =
        testApplication {
            val response = get("/v1/point") { url { parameters["id"] = "2000000001" } }
            Assertions.assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    @DisplayName("存在しないIDの場合保有ポイントが存在しないエラーとなる")
    fun `存在しないIDの場合保有ポイントが存在しないエラーとなる`() =
        testApplication {
            val response = get("/v1/point") { url { parameters["id"] = "4" } }
            Assertions.assertEquals(HttpStatusCode.NotFound, response.status)
        }
}
