package lotto.model


enum class LottoMessage(val message: String) {
    REQUEST_PURCHASE_AMOUNT("구입금액을 입력해 주세요."),
    REQUEST_WINNING_NUMBERS("\n당첨 번호를 입력해 주세요."),
    REQUEST_BONUS_NUMBER("\n보너스 번호를 입력해 주세요."),

    PURCHASE_COUNT_INFO("\n%d개를 구매했습니다."),
    STATISTICS_HEADER("\n당첨 통계\n---"),
    STATISTICS_RESULT_ENTRY("%d개 일치 (%s원) - %d개"),
    STATISTICS_BONUS_RESULT_ENTRY("%d개 일치, 보너스 볼 일치 (%s원) - %d개"),
    RATE_OF_RETURN("총 수익률은 %.1f%%입니다.");

    fun format(vararg args: Any): String = String.format(message, *args)
}