package lotto

import lotto.LottoConstants.PRICE


enum class LottoErrorMessage(val message: String) {
    ERROR_PREFIX("[ERROR] "),
    ERROR_EMPTY_INPUT("입력값이 비어 있습니다."),
    ERROR_NOT_A_NUMBER("숫자 혹은 쉼표로 구분된 숫자 목록을 입력해주세요."), // 조금 더 구체적으로 변경
    ERROR_AMOUNT_LESS_THAN_ZERO("0보다 큰 금액을 입력해주세요."),
    ERROR_INVALID_PURCHASE_UNIT("구입 금액은 ${LottoConstants.PRICE}원 단위여야 합니다."),
    ERROR_INVALID_LOTTO_NUMBER_COUNT("로또 번호는 ${LottoConstants.NUMBERS_COUNT}개여야 합니다."),
    ERROR_NUMBER_OUT_OF_RANGE("번호는 ${LottoConstants.MIN_NUMBER}부터 ${LottoConstants.MAX_NUMBER} 사이의 숫자여야 합니다."),
    ERROR_DUPLICATE_LOTTO_NUMBERS("로또 번호는 중복될 수 없습니다."),

    ERROR_EMPTY_BONUS_NUMBER("빈 문자는 보너스 번호가 될 수 없습니다."),
    ERROR_BONUS_NOT_A_NUMBER("보너스 번호는 문자일 수 없습니다."),
    ERROR_BONUS_OUT_OF_RANGE("보너스 번호는 ${LottoConstants.MIN_NUMBER}부터 ${LottoConstants.MAX_NUMBER} 사이의 숫자여야 합니다."),
    ERROR_BONUS_IN_WINNING_NUMBERS("보너스 번호가 당첨 번호와 중복됩니다.");

    fun getErrorMessage(): String = ERROR_PREFIX.message + this.message
}