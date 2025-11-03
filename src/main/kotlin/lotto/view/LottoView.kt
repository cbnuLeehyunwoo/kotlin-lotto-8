package lotto.view

import lotto.model.Lotto
import lotto.model.LottoStatistics

interface LottoView {
    fun readPurchaseAmount(): String
    fun readWinningNumbers(): String
    fun readBonusNumber(): String

    fun showPurchaseResult(purchaseCount: Int, lotto: List<String>)

    fun showStatisticsHeader()
    fun showRankResults(rankResults: List<String>)
    fun showRateOfReturn(rateOfReturn: Double)

    fun showMessage(message: String)
    fun showError(message: String)
}