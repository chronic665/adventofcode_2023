import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day07Test {

    @Test
    fun testCountLabels() {

        "ABCDE 111".asHand().countLabels() shouldBe 5
        "32T3K 765".asHand().countLabels() shouldBe 4
        "T55J5 684".asHand().countLabels() shouldBe 3
        "KK677 28".asHand().countLabels() shouldBe 3
        "KTJJT 220".asHand().countLabels() shouldBe 3
        "QQQJA 483".asHand().countLabels() shouldBe 3

        "AAAAA 111".asHand().countLabels() shouldBe 1
        "AABBB 111".asHand().countLabels() shouldBe 2
        "AAABC 111".asHand().countLabels() shouldBe 3
        "AAAAB 111".asHand().countLabels() shouldBe 2

    }

    @Test
    fun testBids() {

        "ABCDE 111".asHand().bid shouldBe 111
        "32T3K 765".asHand().bid shouldBe 765
        "T55J5 684".asHand().bid shouldBe 684
        "KK677 28".asHand().bid shouldBe 28
        "KTJJT 220".asHand().bid shouldBe 220
        "QQQJA 483".asHand().bid shouldBe 483

        "AAAAA 111".asHand().bid shouldBe 111
        "AABBB 111".asHand().bid shouldBe 111
        "AAABC 111".asHand().bid shouldBe 111
        "AAAAB 111".asHand().bid shouldBe 111

    }
    @Test
    fun testHands() {

        "32T3K 765".asHand().getType() shouldBe Type.ONE_PAIR
        "T55J5 684".asHand().getType() shouldBe Type.THREE_OF_A_KIND
        "KK677 28".asHand().getType() shouldBe Type.TWO_PAIR
        "KTJJT 220".asHand().getType() shouldBe Type.TWO_PAIR
        "QQQJA 483".asHand().getType() shouldBe Type.THREE_OF_A_KIND

        "AAAAA 111".asHand().getType() shouldBe Type.FIVE_OF_A_KIND
        "AABBB 111".asHand().getType() shouldBe Type.FULL_HOUSE
        "AAABC 111".asHand().getType() shouldBe Type.THREE_OF_A_KIND
        "AAAAB 111".asHand().getType() shouldBe Type.FOUR_OF_A_KIND
    }

    @Test
    fun testJokerHands() {

        "32T3K 765".asHand().getTypeWithJoker() shouldBe Type.ONE_PAIR
        "T55J5 684".asHand().getTypeWithJoker() shouldBe Type.FOUR_OF_A_KIND
        "KK677 28".asHand().getTypeWithJoker() shouldBe Type.TWO_PAIR
        "KTJJT 220".asHand().getTypeWithJoker() shouldBe Type.FOUR_OF_A_KIND
        "QQQJA 483".asHand().getTypeWithJoker() shouldBe Type.FOUR_OF_A_KIND

        "AAAAA 111".asHand().getTypeWithJoker() shouldBe Type.FIVE_OF_A_KIND
        "AABBB 111".asHand().getTypeWithJoker() shouldBe Type.FULL_HOUSE
        "AAABC 111".asHand().getTypeWithJoker() shouldBe Type.THREE_OF_A_KIND
        "AAAAB 111".asHand().getTypeWithJoker() shouldBe Type.FOUR_OF_A_KIND
        "AAAJJ 111".asHand().getTypeWithJoker() shouldBe Type.FIVE_OF_A_KIND
        "AABBJ 111".asHand().getTypeWithJoker() shouldBe Type.FULL_HOUSE
        "AAJBC 111".asHand().getTypeWithJoker() shouldBe Type.THREE_OF_A_KIND
        "AAAJB 111".asHand().getTypeWithJoker() shouldBe Type.FOUR_OF_A_KIND
        "AAJJB 111".asHand().getTypeWithJoker() shouldBe Type.FOUR_OF_A_KIND
        "AJJJB 111".asHand().getTypeWithJoker() shouldBe Type.FOUR_OF_A_KIND
    }

    @Test
    fun testRanks() {
        val result = Day07().getRanks(testInput)
        result.forEach { println(it) }
    }

    @Test
    fun `test mostOftenUsedChars`() {
        val hand = "KTJJT 111".asHand()
        hand.mostOftenUsedChar(hand.cards.groups()) shouldBe 'T'

        val hand3 = "KTKJT 111".asHand()
        hand3.mostOftenUsedChar(hand3.cards.groups()) shouldBe 'K'

        val hand2 = "KTJTT 111".asHand()
        hand2.mostOftenUsedChar(hand2.cards.groups()) shouldBe 'T'
    }

    @Test
    fun value() {
        val cut = Day07()
        val result = cut.calculateTotal(cut.getRanks(testInput))
        result shouldBe 6440
    }

}

val testInput = listOf(
    "32T3K 765",
    "T55J5 684",
    "KK677 28",
    "KTJJT 220",
    "QQQJA 483"
)
