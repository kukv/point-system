package jp.kukv.point.domain.model.transaction

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.UUID

@DisplayName("取引番号テストケース")
class TransactionNumberTest {
    @Test
    @DisplayName("取引番号はUUIDのみで生成可能")
    fun valid() {
        val transactionNumber = TransactionNumber(UUID.randomUUID())
        val validator = TransactionNumber.validator
        val violations = validator.validate(transactionNumber)

        Assertions.assertEquals(0, violations.size)
    }
}
