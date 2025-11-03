package lotto.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LottoMatcherTest {

    private lateinit var prizeNumbers: List<Int>
    private var bonusNumber: Int = 0
    private lateinit var lottoMatcher: LottoMatcher
    @BeforeEach
    fun setUp() {
        prizeNumbers = listOf(1, 2, 3, 4, 5, 6)
        bonusNumber = 7
        lottoMatcher = LottoMatcher(prizeNumbers, bonusNumber)
    }

    @Test
    @DisplayName("6개 번호가 모두 일치하면 1등(FIRST)을 반환한다.")
    fun `1등 테스트`() {
        val userLotto = Lotto(listOf(1, 2, 3, 4, 5, 6))
        val rank = lottoMatcher.calculateRank(userLotto)
        assertThat(rank).isEqualTo(Rank.FIRST)
    }

    @Test
    @DisplayName("5개 번호와 보너스 번호가 일치하면 2등(SECOND)을 반환한다.")
    fun `2등 테스트`() {
        val userLotto = Lotto(listOf(1, 2, 3, 4, 5, 7))
        val rank = lottoMatcher.calculateRank(userLotto)
        assertThat(rank).isEqualTo(Rank.SECOND)
    }

    @Test
    @DisplayName("5개 번호만 일치하고 보너스 번호가 불일치하면 3등(THIRD)을 반환한다.")
    fun `3등 테스트`() {
        val userLotto = Lotto(listOf(1, 2, 3, 4, 5, 8)) // 6, 7 둘 다 미포함
        val rank = lottoMatcher.calculateRank(userLotto)
        assertThat(rank).isEqualTo(Rank.THIRD)
    }

    @Test
    @DisplayName("4개 번호가 일치하면 4등(FOURTH)을 반환한다.")
    fun `4등 테스트`() {
        val userLotto = Lotto(listOf(1, 2, 3, 4, 8, 9))
        val rank = lottoMatcher.calculateRank(userLotto)
        assertThat(rank).isEqualTo(Rank.FOURTH)
    }

    @Test
    @DisplayName("4개 번호와 보너스 번호가 일치해도 4등(FOURTH)을 반환한다.")
    fun `4등과 보너스 일치 테스트`() {
        val userLotto = Lotto(listOf(1, 2, 3, 4, 7, 9))
        val rank = lottoMatcher.calculateRank(userLotto)

        assertThat(rank).isEqualTo(Rank.FOURTH)
    }

    @Test
    @DisplayName("3개 번호가 일치하면 5등(FIFTH)을 반환한다.")
    fun `5등 테스트`() {
        val userLotto = Lotto(listOf(1, 2, 3, 8, 9, 10))
        val rank = lottoMatcher.calculateRank(userLotto)
        assertThat(rank).isEqualTo(Rank.FIFTH)
    }

    @Test
    @DisplayName("2개 이하의 번호가 일치하면 낙첨(MISS)을 반환한다.")
    fun `낙첨 테스트`() {
        val userLotto = Lotto(listOf(1, 2, 8, 9, 10, 11))
        val rank = lottoMatcher.calculateRank(userLotto)
        assertThat(rank).isEqualTo(Rank.MISS)
    }
}