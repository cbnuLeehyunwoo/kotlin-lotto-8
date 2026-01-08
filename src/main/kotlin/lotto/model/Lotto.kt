package lotto.model

class Lotto(private val numbers: List<Int>) {
    init {
        require(numbers.size == 6) { "[ERROR] 로또 번호는 6개여야 합니다." }
        require(numbers.toSet().size == numbers.size) { "[ERROR] 로또 번호는 중복될 수 없습니다." }
    }
    fun getNumbers() = numbers

    // TODO: 추가 기능 구현
}
