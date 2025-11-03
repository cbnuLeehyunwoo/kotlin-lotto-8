package lotto.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LottoTest {

    @Test
    @DisplayName("로또 번호의 개수가 6개가 아니면 예외가 발생한다.")
    fun `로또 번호가 6개가 아니면 예외 발생`() {
        assertThrows<IllegalArgumentException> {
            Lotto(listOf(1, 2, 3, 4, 5, 6, 7))
        }
    }

    @Test
    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    fun `로또 번호에 중복이 있으면 예외 발생`() {
        assertThrows<IllegalArgumentException> {
            Lotto(listOf(1, 2, 3, 4, 5, 5))
        }
    }

    @Test
    @DisplayName("로또 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
    fun `로또 번호 범위가 틀리면 예외 발생`() {
        assertThrows<IllegalArgumentException> {
            Lotto(listOf(1, 2, 3, 4, 5, 46))
        }
    }

    @Test
    @DisplayName("hasNumber 메소드는 번호 포함 여부를 정확히 반환한다.")
    fun `번호 포함 여부 테스트`() {
        val lotto = Lotto(listOf(1, 2, 3, 4, 5, 6))
        assertThat(lotto.hasNumber(3)).isTrue()
        assertThat(lotto.hasNumber(7)).isFalse()
    }

    @Test
    @DisplayName("toString 메소드는 번호를 정렬하여 지정된 형식의 문자열로 반환한다.")
    fun `toString 포맷 및 정렬 테스트`() {
        val lotto = Lotto(listOf(8, 21, 23, 41, 1, 15))
        val lottoAsString = lotto.toString()
        assertThat(lottoAsString).isEqualTo("[1, 8, 15, 21, 23, 41]")
    }

    @Test
    @DisplayName("matchNumberCount 메소드는 일치하는 번호의 개수를 정확히 반환한다.")
    fun `당첨 번호와 일치 개수 계산 테스트`() {
        val userLotto = Lotto(listOf(1, 2, 3, 4, 5, 6))
        val prizeNumbers1 = listOf(1, 2, 3, 10, 11, 12)
        val prizeNumbers2 = listOf(1, 2, 3, 4, 5, 6)
        val prizeNumbers3 = listOf(10, 11, 12, 13, 14, 15)

        val matchCount1 = userLotto.matchNumberCount(prizeNumbers1)
        val matchCount2 = userLotto.matchNumberCount(prizeNumbers2)
        val matchCount3 = userLotto.matchNumberCount(prizeNumbers3)

        assertThat(matchCount1).isEqualTo(3)
        assertThat(matchCount2).isEqualTo(6)
        assertThat(matchCount3).isEqualTo(0)
    }
}