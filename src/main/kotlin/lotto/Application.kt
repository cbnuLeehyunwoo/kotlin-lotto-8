package lotto

import camp.nextstep.edu.missionutils.Randoms.pickUniqueNumbersInRange
import camp.nextstep.edu.missionutils.Console.readLine

fun main() {
    // TODO: 프로그램 구현
    val lottoPurchaseAmount = getLottoPurchaseAmount()
    val lottoCount = calculateLottoCount(lottoPurchaseAmount)
    println("\n${lottoCount}개를 구매했습니다.")
    val userLotto: List<Lotto> = getUserLotto(lottoCount)
    displayUserLotto(userLotto)
    val prizeNumbers = getPrizeNumber()
    val bonusNumber = getBonusNumber()
    val lottoMatcher = LottoMatcher(prizeNumbers, bonusNumber)
    val lottoStatistics = LottoStatistics(userLotto, lottoMatcher)
    displayStatistics(lottoStatistics, lottoPurchaseAmount)
}

fun displayStatistics(statistics: LottoStatistics, purchaseAmount: Int) {
    println("\n당첨 통계")
    println("---")
    displayRankResults(statistics)
    displayRateOfReturn(statistics, purchaseAmount)
}

private fun displayRankResults(statistics: LottoStatistics) {
    Rank.entries
        .filter { it != Rank.MISS }
        .sortedBy { it.prize }
        .forEach { rank ->
            val count = statistics.rankCounts.getOrDefault(rank, 0)
            val prize = String.format("%,d", rank.prize)
            val matchInfo = buildMatchInfo(rank)
            println("$matchInfo (${prize}원) - ${count}개")
        }
}

private fun buildMatchInfo(rank: Rank): String =
    when (rank) {
        Rank.SECOND -> "${rank.matchCount}개 일치, 보너스 볼 일치"
        else -> "${rank.matchCount}개 일치"
    }

private fun displayRateOfReturn(statistics: LottoStatistics, purchaseAmount: Int) {
    val rate = statistics.calculateRateOfReturn(purchaseAmount)
    val formatted = String.format("%.1f", rate)
    println("총 수익률은 ${formatted}%입니다.")
}
fun getPrizeNumber(): List<Int> {
    println("\n당첨 번호를 입력해 주세요")
    return readLine().split(",").map { it.toInt() }
}

fun getBonusNumber(): Int {
    println("\n보너스 번호를 입력해 주세요")
    return readLine().toInt()
}

fun getUserLotto(lottoCount: Int): List<Lotto> =
    List(lottoCount) { Lotto(pickUniqueNumbersInRange(1, 45, 6)) }

fun getLottoPurchaseAmount(): Int {
    println("구입금액을 입력해 주세요.")
    return readLine().toInt()
}

fun calculateLottoCount(amount: Int): Int {
    return amount / 1000
}

fun displayUserLotto(userLotto: List<Lotto>) = userLotto.forEach { println(it) }
