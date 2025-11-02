package lotto

import lotto.model.LottoConstants.MIN_NUMBER
import lotto.model.LottoConstants.MAX_NUMBER
import lotto.model.LottoConstants.PRICE
import lotto.model.LottoMessage.*
import lotto.model.LottoErrorMessage.*
import camp.nextstep.edu.missionutils.Randoms.pickUniqueNumbersInRange
import camp.nextstep.edu.missionutils.Console.readLine
import lotto.model.Lotto
import lotto.model.LottoMatcher
import lotto.model.LottoStatistics
import lotto.model.Rank

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
    require(purchaseAmount.isNotEmpty()) { ERROR_EMPTY_INPUT.getErrorMessage() }
    require(purchaseAmount.toIntOrNull() != null) { ERROR_NOT_A_NUMBER.getErrorMessage() }
    val amount = purchaseAmount.toInt()
    require(amount > 0) { ERROR_AMOUNT_LESS_THAN_ZERO.getErrorMessage() }
    require(amount % PRICE == 0) { ERROR_INVALID_PURCHASE_UNIT.getErrorMessage() }
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
    require(prizeNumbers.all { it.isNotEmpty() }) { ERROR_INVALID_LOTTO_NUMBER_COUNT.getErrorMessage() }
    require(prizeNumbers.all { it.toIntOrNull() != null }) { ERROR_NOT_A_NUMBER.getErrorMessage() }
    require(prizeNumbers.size == 6) { ERROR_INVALID_LOTTO_NUMBER_COUNT.getErrorMessage() }
    val numbers = prizeNumbers.map { it.toInt() }
    require(numbers.all { it in 1..45 }) { ERROR_NUMBER_OUT_OF_RANGE.getErrorMessage() }
    require(numbers.size == numbers.toSet().size) { ERROR_DUPLICATE_LOTTO_NUMBERS }
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
    require(bonusNumber.isNotEmpty()) { ERROR_EMPTY_BONUS_NUMBER.getErrorMessage() }
    require(bonusNumber.toIntOrNull() != null) { ERROR_BONUS_NOT_A_NUMBER.getErrorMessage() }
    val amount = bonusNumber.toInt()
    require(amount in MIN_NUMBER..MAX_NUMBER) { ERROR_NUMBER_OUT_OF_RANGE.getErrorMessage() }
    require(amount !in prizeNumbers) { ERROR_BONUS_IN_WINNING_NUMBERS.getErrorMessage() }
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
