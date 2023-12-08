import java.io.File
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.math.min
import kotlin.streams.toList

data class ScratchCardGame(val winningNumbers: Set<Int>, val myNumbers: Set<Int>, val number: Int? = null, val originalCard: Int? = number)

fun main(vararg args: String) {
//    val lines = File("day_04/example_input_02.txt").readLines()
    val lines = File("day_04/input.txt").readLines()
//    Day04().part01(lines)
    Day04().part02(lines)
}

class Day04 {

    fun part01(lines: List<String>) {
        val score = lines
            .map { parseGame(it) }
            .sumOf { getCardScore(it) }
        println(score)
    }

    fun part02(lines: List<String>) {
        val cards = IntStream.range(0, lines.size)
            .boxed()
            .map {
                val game = parseGame(lines[it])
                ScratchCardGame(game.winningNumbers, game.myNumbers, it)
            }
            .collect(Collectors.toList())
//        println("deck size: ${cards.size}")
        var i = cards.size - 1
        val depths = IntStream.range(0,cards.size).map { 0 } .toArray()
        println("checking cards: $i vs cards: ${cards.size}")
        println("cards: $cards")
        while (i >= 0) {
            println("now collecting card $i")
            val card = cards[i]
            val winners = collectWinningNumbers(card).count()
            val maxIndex = min(cards.size, i + winners)

            var depth = 1
            for (j in i+1..maxIndex) {
                depth += depths[j]
            }
            depths[i] = depth

            i--
        }
        val result = depths.reduce { acc, second -> acc + second }
        println("total scratch cards: $result")
    }

    fun collectCardCopiesByNumber(cards: List<ScratchCardGame>, card: ScratchCardGame, winners: Int) : List<ScratchCardGame> {
        val requiredRange = (card.originalCard!! + 1).rangeTo(card.originalCard + winners).toSet()
//        println("winners: $requiredRange")
        return cards
            .filter { c -> requiredRange.any { c.number == it }  }
            .map { it.copy(number = null) }
    }

    fun collectWinningNumbers(game: ScratchCardGame) : List<Int> {
        return game.myNumbers
            .filter { game.winningNumbers.contains(it) }
    }

    fun getCardScore(game: ScratchCardGame) : Int {
        val winningNumbers = collectWinningNumbers(game)

        if (winningNumbers.isEmpty()) return 0
        if (winningNumbers.size == 1) return 1

        return winningNumbers
            .map { 1 }
            .reduce { acc, _ -> acc * 2}
    }

    fun parseGame(line: String) : ScratchCardGame {
        val numbers = line.split(":")[1].trim().split("|")
        return ScratchCardGame(
            numbers[0].asNumberSet(),
            numbers[1].asNumberSet()
        )
    }
}

fun String.asNumberSet() = this.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()