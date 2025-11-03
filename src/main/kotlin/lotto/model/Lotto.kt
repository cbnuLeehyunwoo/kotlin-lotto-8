package lotto.model
class Lotto(private val numbers: List<Int>) {
    init {
        require(numbers.size == LottoConstants.NUMBERS_COUNT) { LottoErrorMessage.ERROR_INVALID_LOTTO_NUMBER_COUNT.getErrorMessage() }
        require(numbers.size == numbers.toSet().size) { LottoErrorMessage.ERROR_DUPLICATE_LOTTO_NUMBERS.getErrorMessage() }
        require(numbers.all { it in LottoConstants.MIN_NUMBER..LottoConstants.MAX_NUMBER }) { LottoErrorMessage.ERROR_BONUS_OUT_OF_RANGE.getErrorMessage() }
    }

    override fun toString(): String {
        return numbers.sorted().joinToString ( ", ", "[", "]" )
    }

    fun hasNumber(number: Int): Boolean = numbers.contains(number)
    fun matchNumberCount(prizeNumbers: List<Int>) = numbers.toSet().intersect(prizeNumbers.toSet()).size
}
