package jp.kukv.point.domain.model.transaction

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@DisplayName("取引種別テストケース")
class TransactionTypeTest {
    @ParameterizedTest
    @DisplayName("キャンセル可能な取引種別を判別できる")
    @EnumSource(value = TransactionType::class, names = ["獲得", "利用"])
    fun キャンセル可能な取引種別を判別できる(type: TransactionType) {
        Assertions.assertTrue(type.isキャンセル可能な取引種別())
    }

    @ParameterizedTest
    @DisplayName("キャンセル不可能な取引種別を判別できる")
    @EnumSource(value = TransactionType::class, names = ["失効", "キャンセル"])
    fun キャンセル不可能な取引種別を判別できる(type: TransactionType) {
        Assertions.assertFalse(type.isキャンセル可能な取引種別())
    }
}
