package lotto.view

import lotto.model.Lotto
import lotto.model.LottoStatistics

interface LottoView {
    fun readPurchaseAmount(): String
    fun readWinningNumbers(): String
    fun readBonusNumber(): String

    fun showPurchaseResult(lotto: List<Lotto>)
    fun showStatistics(statistics: LottoStatistics, purchaseAmount: Int)
    fun showMessage(message: String)
    fun showError(message: String)
}