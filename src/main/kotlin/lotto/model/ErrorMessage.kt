package oncall.model

enum class ErrorMessage(val message: String) {
    ERROR_PREFIX("[ERROR] "),
    INVALID_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_ORDER("유효하지 않은 주문입니다. 다시 입력해 주세요.");

    // [ERROR] 를 자동으로 붙여서 반환하는 헬퍼
    fun get(): String = "$ERROR_PREFIX$message"
}