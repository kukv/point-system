package jp.kukv.point.domain.model.point

import jp.kukv.point.domain.model.check.SubTotalAmount
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("ポイント付与率テストケース")
class GrantRateTest {
    @ParameterizedTest
    @DisplayName("小計から決められたポイントを算出できる")
    @ValueSource(ints = [100])
    fun `小計から決められたポイントを算出できる`(value: Int) {
        val subTotalAmount = SubTotalAmount(value)
        val grantRate = GrantRate.prototype()

        val point = grantRate.toPoint(subTotalAmount)

        Assertions.assertEquals(1, point())
    }
}
