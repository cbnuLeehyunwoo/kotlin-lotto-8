package lotto

class Lotto(private val numbers: List<Int>) {
    init {
        require(numbers.size == 6) { "[ERROR] 로또 번호는 6개여야 합니다." }
    }

    override fun toString(): String {
        return numbers.sorted().joinToString ( ", ", "[", "]" )
    }

    fun getPrizeNumCount(prizeNumbers: List<Int>) =
        numbers.toSet().intersect(prizeNumbers.toSet()).size
}
