package oncall.model

// 좋은 Enum 활용 예시 (이벤트 배지)
enum class EnumClass(val threshold: Int, val label: String) {
    SANTA(20_000, "산타"),
    TREE(10_000, "트리"),
    STAR(5_000, "별"),
    NONE(0, "없음");

    // "기능"을 가질 수 있음!
    companion object {
        fun getBadgeByAmount(amount: Int): EnumClass {
            return entries.firstOrNull { amount >= it.threshold } ?: NONE
        }
    }
}