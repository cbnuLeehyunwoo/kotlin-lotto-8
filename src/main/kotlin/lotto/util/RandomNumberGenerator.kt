package lotto.util
import camp.nextstep.edu.missionutils.Randoms
import lotto.model.LottoConstants
import lotto.model.NumberGenerator
class RandomNumberGenerator : NumberGenerator {
    override fun generate(): List<Int> {
        return Randoms.pickUniqueNumbersInRange(
            LottoConstants.MIN_NUMBER,
            LottoConstants.MAX_NUMBER,
            LottoConstants.NUMBERS_COUNT
        )
    }
}