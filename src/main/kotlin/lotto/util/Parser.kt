package lotto.util

object Parser {
    // 예: "타파스-1,제로콜라-2" -> Map<String, Int> 변환
    fun parseInputToMap(input: String): Map<String, Int> {
        // 1. 콤마로 분리
        val items = input.split(",")

        // 2. 각 아이템을 맵으로 변환
        return items.associate { item ->
            val parts = item.split("-")
            require(parts.size == 2) { "[ERROR] 올바르지 않은 주문 형식입니다." }

            val name = parts[0].trim()
            val count = parts[1].trim().toIntOrNull()
                ?: throw IllegalArgumentException("[ERROR] 수량은 숫자여야 합니다.")

            name to count
        }
    }
}