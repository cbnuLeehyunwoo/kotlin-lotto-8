package lotto.view

import kotlin.math.abs

object OutputView {
    fun printError(string: String) {
        println(string)
    }
}
    fun Int.toDecimal(): String {
        return "%,d".format(this)
    }

    // 음수까지 가능
    fun Int.toDecimalNegativeVersion(): String {
        val negative = this < 0
        val s = abs(this).toString()

        val formatted = s.reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()

        return if (negative) "-$formatted" else formatted
    }

    // Long 버전
    fun Long.toDecimal(): String {
        val negative = this < 0
        val s = abs(this).toString()

        val formatted = s.reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()

        return if (negative) "-$formatted" else formatted
    }

