package lotto.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LottoPurchaserTest {


    class MockNumberGenerator(
        private val numbersToReturn: List<List<Int>>
    ) : NumberGenerator {
        private var callCount = 0

        override fun generate(): List<Int> {
            return numbersToReturn[callCount++]
        }

        fun getCallCount(): Int = callCount
    }

    @Test
    @DisplayName("구입 금액에 해당하는 개수만큼 로또를 발행한다.")
    fun `로또 구매 개수 테스트`() {
        val amount = 8000
        val expectedCount = 8
        val validNumbers = listOf(1, 2, 3, 4, 5, 6)

        val numberGenerator = MockNumberGenerator(List(expectedCount) { validNumbers })
        val lottoPurchaser = LottoPurchaser(numberGenerator)

        val lotto = lottoPurchaser.purchaseLotto(amount)
        assertThat(lotto).hasSize(expectedCount)
        assertThat(numberGenerator.getCallCount()).isEqualTo(expectedCount)
        assertThat(lotto.first().toString()).isEqualTo("[1, 2, 3, 4, 5, 6]")
    }

    @Test
    @DisplayName("번호 생성기가 중복 번호를 생성하면, 유효한 번호가 나올 때까지 재시도한다.")
    fun `로또 번호 생성 재시도 테스트`() {
        val amount = 1000
        val invalidNumbers = listOf(1, 1, 2, 3, 4, 5)
        val validNumbers = listOf(1, 2, 3, 4, 5, 6)

        val numberGenerator = MockNumberGenerator(listOf(invalidNumbers, validNumbers))
        val lottoPurchaser = LottoPurchaser(numberGenerator)
        val lotto = lottoPurchaser.purchaseLotto(amount)

        assertThat(lotto).hasSize(1)
        assertThat(numberGenerator.getCallCount()).isEqualTo(2)
        assertThat(lotto.first().toString()).isEqualTo("[1, 2, 3, 4, 5, 6]")
    }
}