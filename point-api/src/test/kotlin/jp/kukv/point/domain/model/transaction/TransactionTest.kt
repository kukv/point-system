package jp.kukv.point.domain.model.transaction

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import jp.kukv.point.domain.model.point.Point
import jp.kukv.point.extensions.kotlinx.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@DisplayName("取引テストケース")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionTest {
    @BeforeAll
    fun setup() {
        mockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
    }

    @AfterAll
    fun finish() {
        unmockkStatic("jp.kukv.point._extensions.kotlinx.DateTimeExtensionsKt")
    }

    @ParameterizedTest
    @DisplayName("キャンセルまたは失効の取引の場合キャンセル不可な取引であることを確認できる")
    @EnumSource(TransactionType::class, names = ["キャンセル", "失効"])
    fun `キャンセルまたは失効の取引の場合キャンセル不可な取引であることを確認できる`(type: TransactionType) {
        val transactionTime = TransactionTime(LocalDateTime.parse("2023-11-03T00:00:00"))
        val point = Point(10)
        val transactionNumber = TransactionNumber.create()
        val transaction = Transaction(transactionNumber, point, type, transactionTime)

        every { LocalDate.now() } returns LocalDate.parse("2023-11-08")
        Assertions.assertTrue(transaction.isキャンセル不可())
    }

    @ParameterizedTest
    @DisplayName("取引日時が1ヶ月経過している取引の場合キャンセル不可な取引であることを確認できる")
    @EnumSource(TransactionType::class, names = ["利用", "獲得"])
    fun `取引日時が1ヶ月経過している取引の場合キャンセル不可な取引であることを確認できる`(type: TransactionType) {
        val transactionTime = TransactionTime(LocalDateTime.parse("2023-11-03T00:00:00"))
        val point = Point(10)
        val transactionNumber = TransactionNumber.create()
        val transaction = Transaction(transactionNumber, point, type, transactionTime)

        every { LocalDate.now() } returns LocalDate.parse("2023-12-03")
        Assertions.assertTrue(transaction.isキャンセル不可())
    }

    @ParameterizedTest
    @DisplayName("利用または獲得かつ取引日時が1ヶ月経過していない取引の場合キャンセル不可な取引であることを確認できる")
    @EnumSource(TransactionType::class, names = ["利用", "獲得"])
    fun `利用または獲得かつ取引日時が1ヶ月経過していない取引の場合キャンセル不可な取引であることを確認できる`(type: TransactionType) {
        val transactionTime = TransactionTime(LocalDateTime.parse("2023-11-03T00:00:00"))
        val point = Point(10)
        val transactionNumber = TransactionNumber.create()
        val transaction = Transaction(transactionNumber, point, type, transactionTime)

        every { LocalDate.now() } returns LocalDate.parse("2023-12-02")
        Assertions.assertFalse(transaction.isキャンセル不可())
    }
}
