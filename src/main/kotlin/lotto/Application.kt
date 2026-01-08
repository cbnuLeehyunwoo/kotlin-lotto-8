package lotto

import camp.nextstep.edu.missionutils.Console.readLine
import camp.nextstep.edu.missionutils.Randoms.pickUniqueNumbersInRange
import lotto.model.Lotto
import lotto.model.ModelConstants
import java.util.Locale
import kotlin.math.round

fun main() {
    println("구입 금액을 입력해 주세요.")
    var input = 0
    try {
         input = readLine().toIntOrNull()
            ?: throw IllegalArgumentException("[ERROR] 구입 금액은 숫자여야 합니다")
    } catch (e: IllegalArgumentException) {
        println(e.message ?: "유효하지 않은 입력입니다.")
    }
    val buyCount = getLottoCount(input)
    printLottoCount(buyCount)
    val lottoNumbers = getLottoNumbers(buyCount)
    printLottoNumbers(lottoNumbers)

    val prizeNumber = readLine().split(",").map { it.toInt() }
    val bonusNumber = readLine().toInt()
    val matchResult = lottoNumbers.associateWith {
        var match = 0
        var bonusMatched = false
        it.getNumbers().forEach { number ->
            if (prizeNumber.contains(number)) match++
            if (bonusNumber == number) bonusMatched = true
        }
        Rank.fromMatchCount(match, bonusMatched)
    }
    val rankCount = getRankCount(matchResult)
    val returnOfInvestment = getROI(buyCount, matchResult)

    println("\n당첨 통계")
    println("---")
    val ridFailResult = rankCount.filterNot { it.key.prize == 0 }
    ridFailResult.forEach {
        var matchedInfo = "${it.key.matchCount}개 일치"
        if(it.key == Rank.TWO) matchedInfo += ", 보너스 볼 일치"

        val output = "$matchedInfo (${it.key.prize.toDecimal()}원) - ${it.value}개"
        println(output)
    }
    print("총 수익률은 $returnOfInvestment%입니다.")
}

fun Int.toDecimal(): String {
    val s = this.toString()
    val isNegative = s.startsWith("-")
    val content = s.removePrefix("-")
    val formatted = content.reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
    return if (isNegative) "-$formatted" else formatted
}

fun getRankCount(matchResult: Map<Lotto, Rank>): Map<Rank, Int> {
    val rankCount = matchResult.values.groupingBy({ it }).eachCount()

    val resultMap = Rank.entries.associateWith {
        val value = rankCount[it] ?: 0
        value
    }

    return resultMap
}

fun getROI(buyCount: Int, matchResult: Map<Lotto, Rank>): Double {
    val seedMoney = buyCount * ModelConstants.LOTTO_PRICE
    val resultMoney = matchResult.values.sumOf { it.prize }
    val roi = resultMoney / seedMoney
    return round(roi * 100.0) / 100.0
}


enum class Rank(val matchCount: Int = 0, val prize: Int) {
    FAIL(prize = 0),

    FIVE(3, 5000),
    FOUR(4, 50000),
    THREE(5, 1500000),
    TWO(5, 30000000),
    ONE(6, 2000000000);


    companion object {
        fun fromMatchCount(matchCount: Int, bonusMatched: Boolean): Rank {
            var result = Rank.entries.find { it.matchCount == matchCount } ?: FAIL
            if (result.matchCount == 5 && bonusMatched) result = Rank.TWO

            return result
        }
    }
}

fun getLottoNumbers(buyCount: Int): List<Lotto> {
    return (1..buyCount).map {
        Lotto(pickUniqueNumbersInRange(1, 45, 6))
    }
}

fun getLottoCount(money: Int) = money / 1000

fun printLottoCount(count: Int) {
    println("\n${count}개를 구매했습니다.")
}

fun printLottoNumbers(lottoNumbers: List<Lotto>) {
    lottoNumbers.forEach {
        println(it.getNumbers().sorted())
    }
}