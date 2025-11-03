package lotto.view

import camp.nextstep.edu.missionutils.Console
import lotto.model.LottoMessage

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

    override fun showPurchaseResult(purchaseCount: Int, lotto: List<String>) {
        println(LottoMessage.PURCHASE_COUNT_INFO.format(purchaseCount))
        lotto.forEach { println(it) }
    }

    override fun showStatisticsHeader() {
        println(LottoMessage.STATISTICS_HEADER.message)
    }

    override fun showRankResults(rankResults: List<String>) {
        rankResults.forEach { println(it) }
    }

    override fun showRateOfReturn(rateOfReturn: Double) {
        println(LottoMessage.RATE_OF_RETURN.format(rateOfReturn))
    }

    override fun showMessage(message: String) {
        println(message)
    }

    override fun showError(message: String) {
        println(message)
    }
}