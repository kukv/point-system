package jp.kukv.point.domain.model.transaction

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import jp.kukv.point._extensions.kotlinx.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@DisplayName("取引種別テストケース")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionTimeTest {
    @BeforeAll
    fun setup() {
        mockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
    }

    @AfterAll
    fun finish() {
        unmockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
    }

    @Test
    @DisplayName("取引日時が2023-11-03の時、2023-12-02時点では1ヶ月経過していないことを確認できる")
    fun `取引日時が2023-11-03の時、2023-12-02時点では1ヶ月経過していないことを確認できる`() {
        val transactionTime = TransactionTime(LocalDateTime.parse("2023-11-03T00:00:00"))
        every { LocalDate.now() } returns LocalDate.parse("2023-12-02")

        Assertions.assertFalse(transactionTime.is取引から1ヶ月以上経過())
    }

    @Test
    @DisplayName("取引日時が2023-11-03の時、2023-12-03時点では1ヶ月経過していることを確認できる")
    fun `取引日時が2023-11-03の時、2023-12-03時点では1ヶ月経過していることを確認できる`() {
        val transactionTime = TransactionTime(LocalDateTime.parse("2023-11-03T00:00:00"))
        every { LocalDate.now() } returns LocalDate.parse("2023-12-03")

        Assertions.assertTrue(transactionTime.is取引から1ヶ月以上経過())
    }
}
