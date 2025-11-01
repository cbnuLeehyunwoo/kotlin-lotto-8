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
}

fun getPrizeNumber(): List<Int> {
    println("\n당첨 번호를 입력해 주세요")
    return readLine().split(",").map { it.toInt() }
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
