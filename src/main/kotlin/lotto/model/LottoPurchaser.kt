package lotto.model

class LottoPurchaser(private val numberGenerator: NumberGenerator) {

    fun purchaseLotto(amount: Int): List<Lotto> {
        val count = amount / LottoConstants.PRICE
        return List(count) { generateLotto() }
    }

    private fun generateLotto(): Lotto {
        while (true) {
            try {
                val numbers = numberGenerator.generate()
                return Lotto(numbers)
            } catch (e: IllegalArgumentException) {
            }
        }
    }
}