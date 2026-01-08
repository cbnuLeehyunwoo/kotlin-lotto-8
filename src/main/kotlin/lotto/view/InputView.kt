package christmas.view

import camp.nextstep.edu.missionutils.Console

private const val STRING_1 = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"

private const val STRING = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"

object InputView {
    fun read1(): String {
        println(STRING_1)
        return readLineClean()
    }

    fun read2(): String {
        println(STRING)
        return readLineClean()
    }

    // 공통적으로 쓰이는 readLine + trim 로직
    private fun readLineClean(): String {
        val input = Console.readLine()
        // null 체크 혹은 빈값 체크가 필요하다면 여기서 1차적으로 수행 가능
        if (input.isNullOrBlank()) throw IllegalArgumentException("[ERROR] 입력값이 없습니다.")
        return input.trim()
    }
}