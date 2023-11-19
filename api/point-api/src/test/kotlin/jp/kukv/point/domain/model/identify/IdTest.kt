package jp.kukv.point.domain.model.identify

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("IDテストケース")
class IdTest {
    @ParameterizedTest
    @DisplayName("IDが1以上2_000_000_000以下の場合エラーにならない")
    @ValueSource(ints = [1, 2_000_000_000])
    fun valid(value: Int) {
        val id = Id(value)
        val validator = Id.validator

        val violation = validator.validate(id)
        Assertions.assertEquals(0, violation.size)
    }

    @ParameterizedTest
    @DisplayName("IDが2_000_000_001の場合エラーになる")
    @ValueSource(ints = [2_000_000_001])
    fun over(value: Int) {
        val id = Id(value)
        val validator = Id.validator

        val violation = validator.validate(id)
        Assertions.assertEquals(1, violation.size)
    }

    @ParameterizedTest
    @DisplayName("IDが0の場合エラーになる")
    @ValueSource(ints = [0])
    fun below(value: Int) {
        val id = Id(value)
        val validator = Id.validator

        val violation = validator.validate(id)
        Assertions.assertEquals(1, violation.size)
    }
}
