package lotto

import lotto.LottoConstants.MIN_NUMBER
import lotto.LottoConstants.MAX_NUMBER
import lotto.LottoConstants.PRICE
import lotto.LottoMessage.*
import camp.nextstep.edu.missionutils.Randoms.pickUniqueNumbersInRange
import camp.nextstep.edu.missionutils.Console.readLine

fun main() {
    // TODO: 프로그램 구현
    val lottoPurchaseAmount = getLottoPurchaseAmount()
    val lottoCount = calculateLottoCount(lottoPurchaseAmount)
    println(PURCHASE_COUNT_INFO.format(lottoCount))
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
    require(amount % PRICE == 0) { "[ERROR] 구입 금액은 1000원 단위여야 합니다." }
}

fun getLottoPurchaseAmount(): Int {
    while (true) {
        println(REQUEST_PURCHASE_AMOUNT.message)
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
        println(REQUEST_WINNING_NUMBERS.message)
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
    require(amount in MIN_NUMBER..MAX_NUMBER) { "[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다." }
    require(amount !in prizeNumbers) { "[ERROR] 보너스 번호가 로또 번호와 중복됩니다." }
}


fun getBonusNumber(prizeNumbers: List<Int>): Int {
    while (true) {
        println(REQUEST_BONUS_NUMBER.message)
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
    println(STATISTICS_HEADER.message)
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
            val matchInfo = buildMatchInfo(rank, prize, count)
            println(matchInfo)
        }
}

private fun buildMatchInfo(rank: Rank, prize: String, count: Int): String =
    when (rank) {
        Rank.SECOND -> STATISTICS_BONUS_RESULT_ENTRY.format(rank.matchCount, prize, count)
        else -> STATISTICS_RESULT_ENTRY.format(rank.matchCount, prize, count)
    }

private fun displayRateOfReturn(statistics: LottoStatistics, purchaseAmount: Int) {
    val rate = statistics.calculateRateOfReturn(purchaseAmount)
    println(RATE_OF_RETURN.format(rate))
}


fun getUserLotto(lottoCount: Int): List<Lotto> =
    List(lottoCount) {
        generateValidLotto()
    }

fun generateValidLotto(): Lotto {
    while (true) {
        try {
            return Lotto(pickUniqueNumbersInRange(1, 45, 6))
        } catch (e: IllegalArgumentException) {}
    }
}
fun calculateLottoCount(amount: Int): Int {
    return amount / PRICE
}

fun displayUserLotto(userLotto: List<Lotto>) = userLotto.forEach { println(it) }
