package jp.kukv.point.domain.model.check

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("小計テストケース")
class SubTotalAmountTest {
    @ParameterizedTest
    @DisplayName("小計が1円以上99_999_999円以下の場合エラーにならない")
    @ValueSource(ints = [1, 99_999_999])
    fun valid(value: Int) {
        val amount = SubTotalAmount(value)
        val validator = SubTotalAmount.validator

        val violation = validator.validate(amount)
        Assertions.assertEquals(0, violation.size)
    }

    @ParameterizedTest
    @DisplayName("小計が100_000_000円の場合エラーになる")
    @ValueSource(ints = [100_000_000])
    fun over(value: Int) {
        val amount = SubTotalAmount(value)
        val validator = SubTotalAmount.validator

        val violation = validator.validate(amount)
        Assertions.assertEquals(1, violation.size)
    }

    @ParameterizedTest
    @DisplayName("小計が0円の場合エラーになる")
    @ValueSource(ints = [0])
    fun below(value: Int) {
        val amount = SubTotalAmount(value)
        val validator = SubTotalAmount.validator

        val violation = validator.validate(amount)
        Assertions.assertEquals(1, violation.size)
    }
}
