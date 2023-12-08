import java.io.File

val cardOrder = listOf('J','2','3','4','5','6','7','8','9','T','Q','K','A')

fun main(vararg args: String) {
//    val file = File("day_07/example_input.txt")
    val file = File("day_07/input.txt")
    Day07().part01(file)
    Day07().part02(file)
}

enum class Type {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
}

data class Card(val label: Char) : Comparable<Card> {
    override fun compareTo(other: Card): Int {
        return cardOrder.indexOf(this.label) compareTo cardOrder.indexOf(other.label)
    }

    override fun toString(): String {
        return label.toString()
    }

    override fun equals(other: Any?): Boolean = when {
        other is Card -> this.label == other.label
        else -> false
    }
}

class JokerComparator : Comparator<Hand> {
    override fun compare(left: Hand, right: Hand): Int =
        when {
            left.getTypeWithJoker() != right.getTypeWithJoker()
                -> left.getTypeWithJoker().ordinal compareTo right.getTypeWithJoker().ordinal
            else -> left.compareCardsWith(right)
        }

}

data class Hand(val cards: List<Card>, val bid: Int) : Comparable<Hand> {

    fun countLabels() : Int {
        return cards.distinct().count()
    }

    fun getType() : Type {
        return when(countLabels()) {
            5 -> Type.HIGH_CARD
            4 -> Type.ONE_PAIR
            3 -> {
                val groups = cards.groupSizes()

                return if (groups.max() == 2) {
                    Type.TWO_PAIR
                } else {
                    Type.THREE_OF_A_KIND
                }
            }
            2 -> {
                return if (cards.groupSizes().max() == 4 ) Type.FOUR_OF_A_KIND
                    else Type.FULL_HOUSE
            }
            1 -> Type.FIVE_OF_A_KIND
            else -> throw RuntimeException("Unexpected hand")
        }
    }

    fun getTypeWithJoker() : Type {
        return if (cards.contains(Card('J'))) {
            val groups = cards.groups()
            if (groups.size == 1) return Type.FIVE_OF_A_KIND
            val mostOftenChar = mostOftenUsedChar(groups)
            val jokeredCards = cards.map { if (it.label == 'J') Card(mostOftenChar) else it }
            val jokeredHand = Hand(jokeredCards, bid)
            println("replace hand $this with $jokeredHand")
            return jokeredHand.getType()
        } else {
            getType()
        }
    }

    fun mostOftenUsedChar(groups: Map<Char, List<Card>>): Char =  groups.entries
            .filter { it.key != 'J' }
            .sortedBy { it.value.size }
            .reversed()[0].key

    override fun compareTo(other: Hand) : Int = when {
        this.getType() != other.getType() -> this.getType().ordinal compareTo other.getType().ordinal
        else -> compareCardsWith(other)
    }

    fun compareCardsWith(other: Hand) : Int {
        for ((idx, card) in cards.withIndex()) {
            val orderOfCards = card compareTo other.cards[idx]
            if (orderOfCards != 0) return orderOfCards
        }
        return 0
    }

    override fun toString(): String {
        return "[${this.cards.joinToString("")}] bid:$bid"
    }

}


class Day07 {

    fun part01(file: File) {
        val result = calculateTotal(getRanks(file.readLines()))
        println("result: $result")
    }

    fun part02(file: File) {
        val result = calculateTotal(getRanksWithJokers(file.readLines()))
        println("result: $result")
    }

    fun getRanksWithJokers(lines: List<String>) : Iterable<IndexedValue<Hand>> {
        return lines.map { it.asHand() }.sortedWith(JokerComparator()).withIndex()
    }

    fun getRanks(lines: List<String>) : Iterable<IndexedValue<Hand>> {
        return lines.map { it.asHand() }.sorted().withIndex()
    }

    fun calculateTotal(ranks: Iterable<IndexedValue<Hand>>) : Long {
        return ranks.map { (it.index + 1) * it.value.bid.toLong() }.sum()
    }
}

fun String.asHand(): Hand {
    val parts = this.split(" ")
    return Hand(parts[0].toCharArray().map { Card(it) }, parts[1].toInt())
}
fun List<Card>.groups() = this.groupBy { it.label }
fun List<Card>.groupSizes() = this.groups().map { it.value.size }