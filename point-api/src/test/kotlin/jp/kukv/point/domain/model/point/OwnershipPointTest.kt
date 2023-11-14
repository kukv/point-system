package jp.kukv.point.domain.model.point

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import jp.kukv.point.extensions.kotlinx.now
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@DisplayName("保有ポイントテストケース")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OwnershipPointTest {
    @BeforeAll
    fun setup() {
        mockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
    }

    @AfterAll
    fun finish() {
        unmockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
    }

    @Test
    @DisplayName("有効期限内の場合既存のポイントに追加分を加算できる")
    fun `有効期限内の場合既存のポイントに追加分を加算できる`() {
        val currentOwnershipPoint = OwnershipPoint(Point(10), ExpirationPeriod(LocalDate.parse("2023-11-03")))
        val additionalOwnershipPoint = OwnershipPoint(Point(10), ExpirationPeriod(LocalDate.parse("2024-11-02")))

        every { LocalDate.now() } returns LocalDate.parse("2023-11-02")
        Assertions.assertDoesNotThrow { currentOwnershipPoint.add(additionalOwnershipPoint) }

        val actual = currentOwnershipPoint.add(additionalOwnershipPoint)
        Assertions.assertEquals(20, actual.point())
        Assertions.assertEquals(LocalDate.parse("2024-11-02"), actual.expirationPeriod())
    }

    @Test
    @DisplayName("有効期限切れの場合ポイント加算分のポイントとなる")
    fun `有効期限切れの場合ポイント加算分のポイントとなる`() {
        val currentOwnershipPoint = OwnershipPoint(Point(10), ExpirationPeriod(LocalDate.parse("2023-11-03")))
        val additionalOwnershipPoint = OwnershipPoint(Point(10), ExpirationPeriod(LocalDate.parse("2024-11-06")))

        every { LocalDate.now() } returns LocalDate.parse("2023-11-06")
        Assertions.assertDoesNotThrow { currentOwnershipPoint.add(additionalOwnershipPoint) }

        val actual = currentOwnershipPoint.add(additionalOwnershipPoint)
        Assertions.assertEquals(10, actual.point())
        Assertions.assertEquals(LocalDate.parse("2024-11-06"), actual.expirationPeriod())
    }

    @Test
    @DisplayName("ポイント利用ができる")
    fun `ポイント利用ができる`() {
        val currentOwnershipPoint = OwnershipPoint(Point(10), ExpirationPeriod(LocalDate.parse("2023-11-03")))
        val point = Point(10)

        every { LocalDate.now() } returns LocalDate.parse("2023-11-02")
        Assertions.assertDoesNotThrow { currentOwnershipPoint.use(point) }

        val actual = currentOwnershipPoint.use(point)
        Assertions.assertEquals(0, actual.point())
        Assertions.assertEquals(LocalDate.parse("2023-11-03"), actual.expirationPeriod())
    }

    @Test
    @DisplayName("ポイント利用キャンセルができる")
    fun `ポイント利用キャンセルができる`() {
        val currentOwnershipPoint = OwnershipPoint(Point(10), ExpirationPeriod(LocalDate.parse("2023-11-03")))
        val point = Point(20)

        every { LocalDate.now() } returns LocalDate.parse("2023-11-02")
        Assertions.assertDoesNotThrow { currentOwnershipPoint.cancel(point) }

        val actual = currentOwnershipPoint.cancel(point)
        Assertions.assertEquals(30, actual.point())
        Assertions.assertEquals(LocalDate.parse("2023-11-03"), actual.expirationPeriod())
    }
}
