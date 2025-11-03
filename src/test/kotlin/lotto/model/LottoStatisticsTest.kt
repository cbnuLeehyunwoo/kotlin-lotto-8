package lotto.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LottoStatisticsTest {

    private lateinit var lottoMatcher: LottoMatcher

    @BeforeEach
    fun setUp() {
        val winningNumbers = listOf(1, 2, 3, 4, 5, 6)
        val bonusNumber = 7
        lottoMatcher = LottoMatcher(winningNumbers, bonusNumber)
    }

    @Test
    @DisplayName("각 등수별 당첨 개수를 정확히 집계한다.")
    fun `등수별 당첨 개수 집계 테스트`() {
        val userLotto = listOf(
            Lotto(listOf(1, 2, 3, 4, 5, 6)), // 1등
            Lotto(listOf(1, 2, 3, 4, 5, 7)), // 2등
            Lotto(listOf(1, 2, 3, 4, 5, 8)), // 3등
            Lotto(listOf(1, 2, 3, 4, 8, 9)), // 4등
            Lotto(listOf(1, 2, 3, 8, 9, 10)),// 5등
            Lotto(listOf(1, 2, 3, 11, 12, 13)),// 5등 (2번째)
            Lotto(listOf(10, 11, 12, 13, 14, 15)) // 꽝 (MISS)
        )

        val statistics = LottoStatistics(userLotto, lottoMatcher)
        val rankCounts = statistics.rankCounts

        assertThat(rankCounts[Rank.FIRST]).isEqualTo(1)
        assertThat(rankCounts[Rank.SECOND]).isEqualTo(1)
        assertThat(rankCounts[Rank.THIRD]).isEqualTo(1)
        assertThat(rankCounts[Rank.FOURTH]).isEqualTo(1)
        assertThat(rankCounts[Rank.FIFTH]).isEqualTo(2)
        assertThat(rankCounts[Rank.MISS]).isEqualTo(1)
    }

    @Test
    @DisplayName("총 수익률을 정확하게 계산한다.")
    fun `수익률 계산 테스트`() {
        val userLotto = listOf(
            Lotto(listOf(1, 2, 3, 10, 11, 12)),
            Lotto(listOf(1, 2, 3, 4, 11, 12)),
            Lotto(listOf(10, 11, 12, 13, 14, 15)),
            Lotto(listOf(16, 17, 18, 19, 20, 21))
        )
        val purchaseAmount = 4000
        val statistics = LottoStatistics(userLotto, lottoMatcher)
        val expectedRateOfReturn = 1375.0
        val rateOfReturn = statistics.calculateRateOfReturn(purchaseAmount)

        assertThat(rateOfReturn).isCloseTo(expectedRateOfReturn, Offset.offset(0.001))
    }

    @Test
    @DisplayName("당첨된 로또가 없으면 수익률은 0.0을 반환한다.")
    fun `당첨 내역 없을 때 수익률 테스트`() {
        val userLottos = listOf(
            Lotto(listOf(10, 11, 12, 13, 14, 15)),
            Lotto(listOf(16, 17, 18, 19, 20, 21))
        )
        val purchaseAmount = 2000
        val statistics = LottoStatistics(userLottos, lottoMatcher)

        val rateOfReturn = statistics.calculateRateOfReturn(purchaseAmount)

        assertThat(rateOfReturn).isEqualTo(0.0)
    }

    @Test
    @DisplayName("구매 금액이 0일 때 수익률은 0.0을 반환한다.")
    fun `구매 금액이 0일 때 수익률 테스트`() {
        val userLottos = emptyList<Lotto>()
        val purchaseAmount = 0
        val statistics = LottoStatistics(userLottos, lottoMatcher)

        val rateOfReturn = statistics.calculateRateOfReturn(purchaseAmount)

        assertThat(rateOfReturn).isEqualTo(0.0)
    }
}