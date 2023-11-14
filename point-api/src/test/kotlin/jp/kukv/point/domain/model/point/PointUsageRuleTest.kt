package jp.kukv.point.domain.model.point

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import jp.kukv.point._extensions.kotlinx.now
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@DisplayName("ポイント利用時のルールチェックテストケース")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PointUsageRuleTest {
    @BeforeAll
    fun setup() {
        mockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
    }

    @AfterAll
    fun finish() {
        unmockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
    }

    @Test
    @DisplayName("有効期限が2023-11-03の1000ポイント保有している場合、2023-11-01付けで1000ポイント利用できる")
    fun `有効期限が2023-11-03の1000ポイント保有している場合、2023-11-01付けで1000ポイント利用できる`() {
        val current = OwnershipPoint(Point(1000), ExpirationPeriod(LocalDate.parse("2023-11-03")))

        every { LocalDate.now() } returns LocalDate.parse("2023-11-01")
        val point = Point(1000)

        val rule = PointUsageRule(current, point)
        Assertions.assertDoesNotThrow { rule.check() }
    }

    @Test
    @DisplayName("有効期限が2023-11-03の1000ポイント保有している場合、2023-11-03付けで1001ポイント利用は不可")
    fun `有効期限が2023-11-03の1000ポイント保有している場合、2023-11-03付けで1001ポイント利用は不可`() {
        val current = OwnershipPoint(Point(1000), ExpirationPeriod(LocalDate.parse("2023-11-03")))

        every { LocalDate.now() } returns LocalDate.parse("2023-11-01")
        val point = Point(1001)

        val rule = PointUsageRule(current, point)
        Assertions.assertThrows(IllegalStateException::class.java) { rule.check() }
    }

    @Test
    @DisplayName("有効期限が2023-11-03の1000ポイント保有している場合、2023-11-04付けで1000ポイント利用は不可")
    fun `有効期限が2023-11-03の1000ポイント保有している場合、2023-11-04付けで1000ポイント利用は不可`() {
        val current = OwnershipPoint(Point(1000), ExpirationPeriod(LocalDate.parse("2023-11-03")))

        every { LocalDate.now() } returns LocalDate.parse("2023-11-04")
        val point = Point(1000)

        val rule = PointUsageRule(current, point)
        Assertions.assertThrows(IllegalStateException::class.java) { rule.check() }
    }
}
