package lotto.util

import lotto.view.OutputView

// 람다 함수를 받아서 예외가 발생하지 않을 때까지 반복 실행
fun <T> retryWhenNoException(action: () -> T): T {
    while (true) {
        try {
            return action()
        } catch (e: IllegalArgumentException) {
            OutputView.printError(e.message ?: "유효하지 않은 입력입니다.")
        }
    }
}

/* 사용법
val date = retryWhenNoException {
    val input = InputView.readDate()
    // 검증 로직 호출
    input // 반환
}
 */