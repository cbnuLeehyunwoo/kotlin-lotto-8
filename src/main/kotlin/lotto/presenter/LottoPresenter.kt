package lotto.presenter

import lotto.model.*
import lotto.view.LottoView
import lotto.util.LottoInputValidator

class LottoPresenter(
    private val view: LottoView,
    private val lottoPurchaser: LottoPurchaser
) {
    fun run() {
        val purchaseAmount = getLottoPurchaseAmount()
        val lotto = lottoPurchaser.purchaseLotto(purchaseAmount)
        val lottoRepresentations = lotto.map { it.toString() }
        view.showPurchaseResult(lotto.size, lottoRepresentations)
        val prizeNumbers = getPrizeNumbers()
        val bonusNumber = getBonusNumber(prizeNumbers)

        val lottoMatcher = LottoMatcher(prizeNumbers, bonusNumber)
        val statistics = LottoStatistics(lotto, lottoMatcher)
        showStatistics(statistics, purchaseAmount)
    }

    private fun getLottoPurchaseAmount(): Int {
        while (true) {
            val purchaseAmount = view.readPurchaseAmount().trim()
            try {
                LottoInputValidator.validatePurchaseAmount(purchaseAmount)
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
                LottoInputValidator.validatePrizeNumber(prizeNumbers)
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
                LottoInputValidator.validateBonusNumber(bonusNumber, prizeNumbers)
                return bonusNumber.toInt()
            } catch (e: IllegalArgumentException) {
                e.message?.let { view.showError(it) }
            }
        }
    }

    private fun showStatistics(statistics: LottoStatistics, purchaseAmount: Int) {
        val rankResultStrings = prepareRankResultStrings(statistics)
        val rateOfReturn = statistics.calculateRateOfReturn(purchaseAmount)

        view.showStatisticsHeader()
        view.showRankResults(rankResultStrings)
        view.showRateOfReturn(rateOfReturn)
    }

    private fun prepareRankResultStrings(statistics: LottoStatistics): List<String> {
        return Rank.entries
            .filter { it != Rank.MISS }
            .sortedBy { it.prize }
            .map { rank ->
                val count = statistics.rankCounts.getOrDefault(rank, 0)
                val prize = String.format("%,d", rank.prize)
                buildMatchInfoMessage(rank, prize, count)
            }
    }

    private fun buildMatchInfoMessage(rank: Rank, prize: String, count: Int): String =
        when (rank) {
            Rank.SECOND -> LottoMessage.STATISTICS_BONUS_RESULT_ENTRY.format(
                rank.matchCount,
                prize,
                count
            )

            else -> LottoMessage.STATISTICS_RESULT_ENTRY.format(rank.matchCount, prize, count)
        }
}