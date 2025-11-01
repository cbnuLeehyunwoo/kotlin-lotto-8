package lotto

import camp.nextstep.edu.missionutils.Console.readLine

fun main() {
    // TODO: 프로그램 구현
    val lottoPurchaseAmount = getLottoPurchaseAmount()
}

fun getLottoPurchaseAmount(): Int {
    println("구입금액을 입력해 주세요.")
    return readLine().toInt()
}