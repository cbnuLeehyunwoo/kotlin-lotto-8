package lotto

import camp.nextstep.edu.missionutils.Console.readLine

fun main() {
    // TODO: 프로그램 구현
    val lottoPurchaseAmount = getLottoPurchaseAmount()
    val lottoCount = calculateLottoCount(lottoPurchaseAmount)
    println("\n${lottoCount}개를 구매했습니다.")
}

fun getLottoPurchaseAmount(): Int {
    println("구입금액을 입력해 주세요.")
    return readLine().toInt()
}

fun calculateLottoCount(amount: Int): Int {
    return amount / 1000
}