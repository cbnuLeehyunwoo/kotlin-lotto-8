package lotto.model

import lotto.model.LottoConstants.MIN_NUMBER
import lotto.model.LottoConstants.MAX_NUMBER
import lotto.model.LottoConstants.NUMBERS_COUNT
import camp.nextstep.edu.missionutils.Randoms

class LottoPurchaser {
    fun purchaseLotto(amount: Int): List<Lotto> {
        val count = amount / LottoConstants.PRICE
        return List(count) { generateLotto() }
    }

    private fun generateLotto(): Lotto {
        while (true) {
            try {
                val numbers = Randoms.pickUniqueNumbersInRange(MIN_NUMBER, MAX_NUMBER, NUMBERS_COUNT)
                return Lotto(numbers)
            } catch (e: IllegalArgumentException) {
            }
        }
    }
}