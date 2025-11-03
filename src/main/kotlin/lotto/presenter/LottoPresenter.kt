package lotto.presenter
import lotto.model.*
import lotto.view.LottoView
class LottoPresenter(
    private val view: LottoView,
    private val lottoPurchaser: LottoPurchaser
) {
    fun run() {
        val purchaseAmount = getLottoPurchaseAmount()
        val lotto = lottoPurchaser.purchaseLotto(purchaseAmount)
        view.showPurchaseResult(lotto)
        val prizeNumbers = getPrizeNumbers()
        val bonusNumber = getBonusNumber(prizeNumbers)

        val lottoMatcher = LottoMatcher(prizeNumbers, bonusNumber)
        val statistics = LottoStatistics(lotto, lottoMatcher)
        view.showStatistics(statistics, purchaseAmount)
    }

    private fun getLottoPurchaseAmount(): Int {
        while (true) {
            val purchaseAmount = view.readPurchaseAmount().trim()
            try {
                validatePurchaseAmount(purchaseAmount)
                return purchaseAmount.toInt()
            } catch (e: IllegalArgumentException) {
                e.message?.let { view.showError(it) }
            }
        }
    }

    private fun getPrizeNumbers(): List<Int> {
        while (true) {
            val input = view.readWinningNumbers().trim()
            val prizeNumbers =
                if (input.isEmpty()) emptyList() else input.split(",").map { it.trim() }
            try {
                validatePrizeNumber(prizeNumbers)
                return prizeNumbers.map { it.toInt() }
            } catch (e: IllegalArgumentException) {
                e.message?.let { view.showError(it) }
            }
        }
    }

    private fun getBonusNumber(prizeNumbers: List<Int>): Int {
        while (true) {
            val bonusNumber = view.readBonusNumber().trim()
            try {
                validateBonusNumber(bonusNumber, prizeNumbers)
                return bonusNumber.toInt()
            } catch (e: IllegalArgumentException) {
                e.message?.let { view.showError(it) }
            }
        }
    }


    private fun validatePurchaseAmount(purchaseAmount: String) {
        require(purchaseAmount.isNotEmpty()) { LottoErrorMessage.ERROR_EMPTY_INPUT.getErrorMessage() }
        val amount = purchaseAmount.toIntOrNull()
            ?: throw IllegalArgumentException(LottoErrorMessage.ERROR_NOT_A_NUMBER.getErrorMessage())
        require(amount > 0) { LottoErrorMessage.ERROR_AMOUNT_LESS_THAN_ZERO.getErrorMessage() }
        require(amount % LottoConstants.PRICE == 0) { LottoErrorMessage.ERROR_INVALID_PURCHASE_UNIT.getErrorMessage() }
    }

    private fun validatePrizeNumber(prizeNumbers: List<String>) {
        try {
            val numbers = prizeNumbers.map { it.toInt() }
            Lotto(numbers)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(LottoErrorMessage.ERROR_NOT_A_NUMBER.getErrorMessage())
        } catch (e: IllegalArgumentException) {
            throw e
        }
    }

    private fun validateBonusNumber(bonusNumber: String, prizeNumbers: List<Int>) {
        require(bonusNumber.isNotEmpty()) { LottoErrorMessage.ERROR_EMPTY_BONUS_NUMBER.getErrorMessage() }
        val number = bonusNumber.toIntOrNull()
            ?: throw IllegalArgumentException(LottoErrorMessage.ERROR_BONUS_NOT_A_NUMBER.getErrorMessage())

        require(number in LottoConstants.MIN_NUMBER..LottoConstants.MAX_NUMBER) { LottoErrorMessage.ERROR_NUMBER_OUT_OF_RANGE.getErrorMessage() }
        require(number !in prizeNumbers) { LottoErrorMessage.ERROR_BONUS_IN_WINNING_NUMBERS.getErrorMessage() }
    }
}