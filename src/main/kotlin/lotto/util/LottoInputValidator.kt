package lotto.util

import lotto.model.Lotto
import lotto.model.LottoErrorMessage
import lotto.model.LottoConstants
object LottoInputValidator {

    fun validatePurchaseAmount(purchaseAmount: String) {
        require(purchaseAmount.isNotEmpty()) { LottoErrorMessage.ERROR_EMPTY_INPUT.getErrorMessage() }
        val amount = purchaseAmount.toIntOrNull()
            ?: throw IllegalArgumentException(LottoErrorMessage.ERROR_NOT_A_NUMBER.getErrorMessage())
        require(amount > 0) { LottoErrorMessage.ERROR_AMOUNT_LESS_THAN_ZERO.getErrorMessage() }
        require(amount % LottoConstants.PRICE == 0) { LottoErrorMessage.ERROR_INVALID_PURCHASE_UNIT.getErrorMessage() }
    }

    fun validatePrizeNumber(prizeNumbers: List<String>) {
        try {
            val numbers = prizeNumbers.map { it.toInt() }
            Lotto(numbers)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(LottoErrorMessage.ERROR_NOT_A_NUMBER.getErrorMessage())
        } catch (e: IllegalArgumentException) {
            throw e
        }
    }

    fun validateBonusNumber(bonusNumber: String, prizeNumbers: List<Int>) {
        require(bonusNumber.isNotEmpty()) { LottoErrorMessage.ERROR_EMPTY_BONUS_NUMBER.getErrorMessage() }
        val number = bonusNumber.toIntOrNull()
            ?: throw IllegalArgumentException(LottoErrorMessage.ERROR_BONUS_NOT_A_NUMBER.getErrorMessage())

        require(number in LottoConstants.MIN_NUMBER..LottoConstants.MAX_NUMBER) { LottoErrorMessage.ERROR_BONUS_OUT_OF_RANGE.getErrorMessage() }
        require(number !in prizeNumbers) { LottoErrorMessage.ERROR_BONUS_IN_WINNING_NUMBERS.getErrorMessage() }
    }
}