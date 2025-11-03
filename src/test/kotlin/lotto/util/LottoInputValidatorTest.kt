package lotto.util

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class LottoInputValidatorTest {

    @Test
    @DisplayName("구입 금액: 유효한 금액(1000원 단위의 양수)은 예외를 발생시키지 않는다.")
    fun `validatePurchaseAmount_유효한_금액`() {
        assertDoesNotThrow {
            LottoInputValidator.validatePurchaseAmount("8000")
        }
    }

    @Test
    @DisplayName("구입 금액: 입력이 비어있으면 예외가 발생한다.")
    fun `validatePurchaseAmount_빈_문자열_입력`() {
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validatePurchaseAmount("")
        }
    }

    @Test
    @DisplayName("구입 금액: 숫자가 아닌 문자가 포함되면 예외가 발생한다.")
    fun `validatePurchaseAmount_숫자가_아닌_입력`() {
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validatePurchaseAmount("1000a")
        }
    }

    @Test
    @DisplayName("구입 금액: 1000원 단위가 아니면 예외가 발생한다.")
    fun `validatePurchaseAmount_1000원_단위가_아닌_금액`() {
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validatePurchaseAmount("1500")
        }
    }

    @Test
    @DisplayName("구입 금액: 0원이면 예외가 발생한다.")
    fun `validatePurchaseAmount_0원_금액`() {
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validatePurchaseAmount("0")
        }
    }


    @Test
    @DisplayName("당첨 번호: 유효한 번호는 예외를 발생시키지 않는다.")
    fun `validatePrizeNumber_유효한_당첨_번호`() {
        assertDoesNotThrow {
            LottoInputValidator.validatePrizeNumber(listOf("1", "2", "3", "4", "5", "6"))
        }
    }

    @Test
    @DisplayName("당첨 번호: 숫자가 아닌 문자가 포함되면 예외가 발생한다.")
    fun `validatePrizeNumber_숫자가_아닌_당첨_번호`() {
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validatePrizeNumber(listOf("1", "2", "3", "a", "5", "6"))
        }
    }

    @Test
    @DisplayName("당첨 번호: 개수가 6개가 아니면 예외가 발생한다.")
    fun `validatePrizeNumber_개수가_6개가_아닌_당첨_번호`() {
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validatePrizeNumber(listOf("1", "2", "3", "4", "5"))
        }
    }

    @Test
    @DisplayName("당첨 번호: 중복된 번호가 있으면 예외가 발생한다.")
    fun `validatePrizeNumber_중복된_당첨_번호`() {
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validatePrizeNumber(listOf("1", "2", "3", "4", "5", "5"))
        }
    }

    @Test
    @DisplayName("당첨 번호: 1-45 범위를 벗어나면 예외가 발생한다.")
    fun `validatePrizeNumber_범위를_벗어난_당첨_번호`() {
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validatePrizeNumber(listOf("1", "2", "3", "4", "5", "46"))
        }
    }


    @Test
    @DisplayName("보너스 번호: 유효한 번호는 예외를 발생시키지 않는다.")
    fun `validateBonusNumber_유효한_보너스_번호`() {
        val prizeNumbers = listOf(1, 2, 3, 4, 5, 6)
        assertDoesNotThrow {
            LottoInputValidator.validateBonusNumber("7", prizeNumbers)
        }
    }

    @Test
    @DisplayName("보너스 번호: 입력이 비어있으면 예외가 발생한다.")
    fun `validateBonusNumber_빈_문자열_보너스_번호`() {
        val prizeNumbers = listOf(1, 2, 3, 4, 5, 6)
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validateBonusNumber("", prizeNumbers)
        }
    }

    @Test
    @DisplayName("보너스 번호: 숫자가 아닌 문자가 포함되면 예외가 발생한다.")
    fun `validateBonusNumber_숫자가_아닌_보너스_번호`() {
        val prizeNumbers = listOf(1, 2, 3, 4, 5, 6)
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validateBonusNumber("a", prizeNumbers)
        }
    }

    @Test
    @DisplayName("보너스 번호: 1-45 범위를 벗어나면 예외가 발생한다.")
    fun `validateBonusNumber_범위를_벗어난_보너스_번호`() {
        val prizeNumbers = listOf(1, 2, 3, 4, 5, 6)
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validateBonusNumber("46", prizeNumbers)
        }
    }

    @Test
    @DisplayName("보너스 번호: 당첨 번호와 중복되면 예외가 발생한다.")
    fun `validateBonusNumber_당첨_번호와_중복된_보너스_번호`() {
        val prizeNumbers = listOf(1, 2, 3, 4, 5, 6)
        assertThrows<IllegalArgumentException> {
            LottoInputValidator.validateBonusNumber("6", prizeNumbers)
        }
    }
}