package lotto.model

class LottoMatcher(
    private val numbers: List<Int>,
    private val bonusNumber: Int
) {
    fun calculateRank(userLotto: Lotto): Rank {
        val matchCount = userLotto.matchNumberCount(numbers)
        val bonusMatched = userLotto.hasNumber(bonusNumber)

        return when(matchCount) {
            6 -> Rank.FIRST
            5 -> if(bonusMatched) Rank.SECOND else Rank.THIRD
            4 -> Rank.FOURTH
            3 -> Rank.FIFTH
            else -> Rank.MISS
        }
    }
}