package jp.kukv.point.domain.model.point

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("ポイントテストケース")
class PointTest {
    @Test
    @DisplayName("足し算ができる")
    fun 足し算ができる() {
        val point1 = Point(100)
        val point2 = Point(100)

        val actual = point1 + point2
        Assertions.assertEquals(200, actual())
    }

    @Test
    @DisplayName("引き算ができる")
    fun 引き算ができる() {
        val point1 = Point(100)
        val point2 = Point(100)

        val actual = point1 - point2
        Assertions.assertEquals(0, actual())
    }

    @ParameterizedTest
    @DisplayName("ポイントが1以上10_000_000以下の場合エラーにならない")
    @ValueSource(ints = [1, 10_000_000])
    fun valid(value: Int) {
        val point = Point(value)
        val validator = Point.validator

        val violation = validator.validate(point)
        Assertions.assertEquals(0, violation.size)
    }

    @ParameterizedTest
    @DisplayName("ポイントが10_000_001の場合エラーになる")
    @ValueSource(ints = [10_000_001])
    fun over(value: Int) {
        val point = Point(value)
        val validator = Point.validator

        val violation = validator.validate(point)
        Assertions.assertEquals(1, violation.size)
    }

    @ParameterizedTest
    @DisplayName("ポイントが0の場合エラーになる")
    @ValueSource(ints = [0])
    fun below(value: Int) {
        val point = Point(value)
        val validator = Point.validator

        val violation = validator.validate(point)
        Assertions.assertEquals(1, violation.size)
    }

    @ParameterizedTest
    @DisplayName("比較対象のポイント以上のポイントを保有している場合、trueを返す")
    @ValueSource(ints = [100, 101])
    fun `比較対象のポイント以上のポイントを保有している場合、trueを返す`(value: Int) {
        val current = Point(value)
        val point = Point(100)

        Assertions.assertTrue(current.hasMore(point))
    }

    @ParameterizedTest
    @DisplayName("比較対象のポイント以上のポイントを保有していない場合、falseを返す")
    @ValueSource(ints = [99])
    fun `比較対象のポイント以上のポイントを保有していない場合、falseを返す`(value: Int) {
        val current = Point(value)
        val point = Point(100)

        Assertions.assertFalse(current.hasMore(point))
    }
}
