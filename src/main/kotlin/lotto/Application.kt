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
    val prizeNumbers = getPrizeNumbers()
    val bonusNumber = getBonusNumber(prizeNumbers)
    val lottoMatcher = LottoMatcher(prizeNumbers, bonusNumber)
    val lottoStatistics = LottoStatistics(userLotto, lottoMatcher)
    displayStatistics(lottoStatistics, lottoPurchaseAmount)
}

fun validatePurchaseAmount(purchaseAmount: String) {
    require(purchaseAmount.isNotEmpty()) { "[ERROR] 입력값이 비어 있습니다." }
    require(purchaseAmount.toIntOrNull() != null) { "[ERROR] 숫자만 입력해주세요." }
    val amount = purchaseAmount.toInt()
    require(amount > 0) { "[ERROR] 0보다 큰 금액을 입력해주세요." }
    require(amount % 1000 == 0) { "[ERROR] 구입 금액은 1000원 단위여야 합니다." }
}

fun getLottoPurchaseAmount(): Int {
    while (true) {
        println("구입금액을 입력해 주세요.")
        val purchaseAmount = readLine().trim()
        try {
            validatePurchaseAmount(purchaseAmount)
            return purchaseAmount.toInt()
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }
}

fun validatePrizeNumber(prizeNumbers: List<String>) {
    require(prizeNumbers.all { it.isNotEmpty() }) { "[ERROR] 빈 문자는 로또 번호가 될 수 없습니다." }
    require(prizeNumbers.all { it.toIntOrNull() != null }) { "[ERROR] 로또 번호는 문자일 수 없습니다." }
    require(prizeNumbers.size == 6) { "[ERROR] 당첨 번호는 6개여야 합니다." }
    val numbers = prizeNumbers.map { it.toInt() }
    require(numbers.all { it in 1..45 }) { "[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다." }
    require(numbers.size == numbers.toSet().size) { "[ERROR] 로또 번호의 중복은 불가능합니다." }
}

fun getPrizeNumbers(): List<Int> {
    while (true) {
        println("\n당첨 번호를 입력해 주세요")
        val input = readLine()?.trim().orEmpty()
        val prizeNumbers = input.split(",").map { it.trim() }
        try {
            validatePrizeNumber(prizeNumbers)
            return prizeNumbers.map { it.toInt() }
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }
}

fun validateBonusNumber(bonusNumber: String, prizeNumbers: List<Int>) {
    require(bonusNumber.isNotEmpty()) { "[ERROR] 빈 문자는 보너스 번호가 될 수 없습니다." }
    require(bonusNumber.toIntOrNull() != null) { "[ERROR] 보너스 번호는 문자일 수 없습니다." }
    val amount = bonusNumber.toInt()
    require(amount in 1..45) { "[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다." }
    require(amount !in prizeNumbers) { "[ERROR] 보너스 번호가 로또 번호와 중복됩니다." }
}


fun getBonusNumber(prizeNumbers: List<Int>): Int {
    while (true) {
        println("\n보너스 번호를 입력해 주세요.")
        val bonusNumber = readLine()?.trim().orEmpty()
        try {
            validateBonusNumber(bonusNumber, prizeNumbers)
            return bonusNumber.toInt()
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }
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


fun getUserLotto(lottoCount: Int): List<Lotto> =
    List(lottoCount) { Lotto(pickUniqueNumbersInRange(1, 45, 6)) }


fun calculateLottoCount(amount: Int): Int {
    return amount / 1000
}

fun displayUserLotto(userLotto: List<Lotto>) = userLotto.forEach { println(it) }
