package lotto

class Lotto(private val numbers: List<Int>) {
    init {
        require(numbers.size == 6) { "[ERROR] 로또 번호는 6개여야 합니다." }
        require(numbers.size == numbers.toSet().size) { "[ERROR] 중복된 번호는 불가능합니다." }
        require(numbers.all { it in 1..45 }) { "[ERROR] 로또 번호는 1부터 45 사이여야 합니다." }
    }

    override fun toString(): String {
        return numbers.sorted().joinToString ( ", ", "[", "]" )
    }

    fun hasNumber(number: Int): Boolean = numbers.contains(number)
    fun matchNumberCount(prizeNumbers: List<Int>) = numbers.toSet().intersect(prizeNumbers.toSet()).size
}
