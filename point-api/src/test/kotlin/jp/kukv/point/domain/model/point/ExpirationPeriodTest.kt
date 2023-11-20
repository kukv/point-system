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

@DisplayName("有効期限テストケース")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExpirationPeriodTest {
    @BeforeAll
    fun setup() {
        mockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
    }

    @AfterAll
    fun finish() {
        unmockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
    }

    @Test
    @DisplayName("有効期限が1年先の日付で生成できる")
    fun `有効期限が1年先の日付で生成できる`() {
        every { LocalDate.now() } returns LocalDate.parse("2023-11-03")

        val expirationPeriod = ExpirationPeriod.prototype()
        Assertions.assertEquals(LocalDate.parse("2024-11-03"), expirationPeriod())
    }

    @Test
    @DisplayName("2024-11-02の日付で2024-11-03の有効期限を確認する場合、有効期限内と判定される")
    fun `2024-11-02の日付で2024-11-03の有効期限を確認する場合、有効期限内と判定される`() {
        every { LocalDate.now() } returns LocalDate.parse("2023-11-03")
        val expirationPeriod = ExpirationPeriod.prototype()

        every { LocalDate.now() } returns LocalDate.parse("2024-11-02")
        Assertions.assertFalse(expirationPeriod.isExpired())
    }

    @Test
    @DisplayName("2024-11-03の日付で2024-11-03の有効期限を確認する場合、有効期限内と判定される")
    fun `2024-11-03の日付で2024-11-03の有効期限を確認する場合、有効期限内と判定される`() {
        every { LocalDate.now() } returns LocalDate.parse("2023-11-03")
        val expirationPeriod = ExpirationPeriod.prototype()
        Assertions.assertFalse(expirationPeriod.isExpired())
    }

    @Test
    @DisplayName("2024-11-04の日付で2024-11-03の有効期限を確認する場合、有効期限切れと判定される")
    fun `2024-11-04の日付で2024-11-03の有効期限を確認する場合、有効期限切れと判定される`() {
        every { LocalDate.now() } returns LocalDate.parse("2023-11-03")
        val expirationPeriod = ExpirationPeriod.prototype()

        every { LocalDate.now() } returns LocalDate.parse("2024-11-04")
        Assertions.assertTrue(expirationPeriod.isExpired())
    }
}
