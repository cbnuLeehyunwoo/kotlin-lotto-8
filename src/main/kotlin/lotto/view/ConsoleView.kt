package lotto.view

import camp.nextstep.edu.missionutils.Console
import lotto.model.Lotto
import lotto.model.LottoMessage
import lotto.model.LottoStatistics
import lotto.model.Rank

class ConsoleView : LottoView {
    override fun readPurchaseAmount(): String {
        println(LottoMessage.REQUEST_PURCHASE_AMOUNT.message)
        return Console.readLine()
    }

    override fun readWinningNumbers(): String {
        println(LottoMessage.REQUEST_WINNING_NUMBERS.message)
        return Console.readLine()
    }

    override fun readBonusNumber(): String {
        println(LottoMessage.REQUEST_BONUS_NUMBER.message)
        return Console.readLine()
    }

    override fun showPurchaseResult(lotto: List<Lotto>) {
        println(LottoMessage.PURCHASE_COUNT_INFO.format(lotto.size))
        lotto.forEach { println(it) }
    }

    override fun showStatistics(statistics: LottoStatistics, purchaseAmount: Int) {
        println(LottoMessage.STATISTICS_HEADER.message)
        displayRankResults(statistics)
        displayRateOfReturn(statistics, purchaseAmount)
    }

    override fun showMessage(message: String) {
        println(message)
    }

    override fun showError(message: String) {
        println(message)
    }

    private fun displayRankResults(statistics: LottoStatistics) {
        Rank.entries
            .filter { it != Rank.MISS }
            .sortedBy { it.prize }
            .forEach { rank ->
                val count = statistics.rankCounts.getOrDefault(rank, 0)
                val prize = String.format("%,d", rank.prize)
                val message = buildMatchInfoMessage(rank, prize, count)
                println(message)
            }
    }

    private fun buildMatchInfoMessage(rank: Rank, prize: String, count: Int): String =
        when (rank) {
            Rank.SECOND -> LottoMessage.STATISTICS_BONUS_RESULT_ENTRY.format(rank.matchCount, prize, count)
            else -> LottoMessage.STATISTICS_RESULT_ENTRY.format(rank.matchCount, prize, count)
        }

    private fun displayRateOfReturn(statistics: LottoStatistics, purchaseAmount: Int) {
        val rate = statistics.calculateRateOfReturn(purchaseAmount)
        println(LottoMessage.RATE_OF_RETURN.format(rate))
    }
}