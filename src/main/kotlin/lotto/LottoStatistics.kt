package lotto

class LottoStatistics(
    private val userLotto: List<Lotto>,
    private val lottoMatcher: LottoMatcher
) {
    val rankCounts: Map<Rank, Int> = countLottoByRank()

    private fun countLottoByRank(): Map<Rank, Int> {
        val result = enumValues<Rank>().associateWith { 0 }.toMutableMap()
        userLotto.forEach { lotto ->
            val rank = lottoMatcher.calculateRank(lotto)
            val currentCount: Int? = result[rank]
            if (currentCount == null) {
                result[rank] = 1
            } else {
                result[rank] = currentCount + 1
            }
        }
        return result
    }
}