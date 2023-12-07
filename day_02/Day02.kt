import java.io.File
import kotlin.math.max

val maxBalls = Round(14, 12, 13)
data class Game(val round: Int, val rounds: List<Round>)
data class Round(val blue: Int = 0, val red: Int = 0, val green: Int = 0)

val redRegEx = "(\\d+) red".toRegex()
val blueRegEx = "(\\d+) blue".toRegex()
val greenRegEx = "(\\d+) green".toRegex()

fun main(vararg args: String) {
    part01()
    part02()
}

fun part01() =
    File("day_02/input.txt")
        .readLines()
        .map { parseGame(it) }
        .filter {
            val roundMax = it.rounds
                .reduce { acc, round ->
                    Round(
                        max(acc.blue, round.blue),
                        max(acc.red, round.red),
                        max(acc.green, round.green)
                    )
                }
            roundMax.blue <= maxBalls.blue && roundMax.red <= maxBalls.red && roundMax.green <= maxBalls.green
        }
        .sumOf { it.round }
        .also { println("Valid games: $it") }


fun part02() =
    File("day_02/input.txt")
        .readLines()
        .map { parseGame(it) }
        .map {
            it.rounds
                .reduce { acc, round ->
                    Round(
                        max(acc.blue, round.blue),
                        max(acc.red, round.red),
                        max(acc.green, round.green)
                    )
                }
        }
        .sumOf { it.blue * it.red * it.green }
        .also { println("Power of games: $it") }


fun parseGame(line: String): Game {
    val parts = line.split(":")
    return Game(parts[0].split(" ")[1].toInt(),
        parts[1]
        .split(";")
        .map { it.trim() }
        .map { Round(it.findPart(blueRegEx), it.findPart(redRegEx), it.findPart(greenRegEx)) }
    )

}

fun String.findPart(regex: Regex) : Int = regex.find(this)?.groupValues?.get(1)?.toInt() ?: 0