package lotto

import lotto.model.LottoPurchaser
import lotto.presenter.LottoPresenter
import lotto.util.RandomNumberGenerator
import lotto.view.ConsoleView

fun main() {
    val numberGenerator = RandomNumberGenerator()
    val lottoPurchaser = LottoPurchaser(numberGenerator)
    val view = ConsoleView()
    val presenter = LottoPresenter(view, lottoPurchaser)
    presenter.run()
}