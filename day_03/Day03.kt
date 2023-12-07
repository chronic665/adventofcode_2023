import java.io.File

data class StateNumber(val number: Int, val row: Int, val minIndex: Int, val maxIndex: Int)

var state: List<String> = listOf()

fun main(vararg args: String) {
    val input = "day_03/input.txt"
    state = File(input).readLines()
    println("matrix size: ${state.size} x ${state.lineLength()}")
    Day03().part01()
    Day03().part02()
}
class Day03 {
    fun part01() {
        var count = 0

        for (row in state.indices) {
            var idx = 0
            while (idx < state.lineLength()) {
                if (isNumber(row, idx)) {
                    val number = collectNumber(row, idx)
                    if (isPartNumber(number)) {
                        print("..${number.number}..")
                        count += number.number
                    }
                    idx = number.maxIndex + 1
                } else {
                    idx++
                }
            }
            println()
        }

        println("Parts count: $count")
    }

    fun part02() {
        var count = 0

        for (row in state.indices) {
            var idx = 0
            print("Found number in row ${row + 1}:")
            while (idx < state.lineLength()) {
                if (isStar(row, idx)) {
                    val numbers = collectNumbersBordering(row, idx)
//                    println("\t$numbers")
                    var values = numbers
                        .map { gatherFullNumbers(it) }
                        .distinctBy { "${it.row}|${it.minIndex}|${it.maxIndex}" }
                        .map { it.number }
                    if (values.size == 2) {
                        val ratio = values.fold(1) { acc, item -> acc * item}
                        print("$values => $ratio | ")
                        count += ratio
                    }
                }
                idx++
            }
            println()
        }

        println("Gear ratio: $count")
    }
}

fun isNumber(row: Int, idx: Int) = isDigit(state[row][idx])
fun isStar(row: Int, idx: Int) = state[row][idx] == '*'
fun isDigit(char: Char) = char.isDigit()

fun collectNumbersBordering(row: Int, idx: Int): List<Pair<Int, Int>> {
    val minRow = if (row == 0) 0 else row - 1
    val maxRow = if (row == state.size - 1) state.size - 1 else row + 1
    val minIndex = if (idx == 0) 0 else idx - 1
    val maxIndex = if (idx == state.lineLength() -1) state.lineLength() -1 else idx + 1
    return collectSymbols(minRow, maxRow, minIndex, maxIndex, ::isDigit)
}

fun collectNumber(row: Int, idx: Int) : StateNumber {
    var num = ""
    for (ptr in idx..<state.lineLength()) {
        val currentChar = state[row][ptr]
        if (currentChar.isDigit()) {
            num += currentChar
        } else {
            return StateNumber(num.toInt(), row, idx, ptr -1)
        }
    }
    return StateNumber(num.toInt(), row, idx, state.lineLength() - 1)
}

fun gatherFullNumbers(point: Pair<Int, Int>) : StateNumber {
    val minIndex = firstNumberIndex(point)
    return collectNumber(point.first, minIndex)
}

fun firstNumberIndex(point: Pair<Int, Int>) : Int {
    if (point.second == 0) return 0
    for (index in point.second downTo 0) {
        if (!state[point.first][index].isDigit()) {
            return index + 1
        }
    }
    return 0
}

fun isPartNumber(number: StateNumber): Boolean {
    val minRow = if (number.row == 0) 0 else number.row - 1
    val maxRow = if (number.row == state.size - 1) state.size - 1 else number.row + 1
    val minIndex = if (number.minIndex == 0) 0 else number.minIndex - 1
    val maxIndex = if (number.maxIndex == state.lineLength() -1) state.lineLength() -1 else number.maxIndex + 1
    return containsSymbol(minRow, maxRow, minIndex, maxIndex, ::isSymbol)
}

fun isSymbol(char: Char) : Boolean {
    return !char.isDigit() && char != '.'
}

fun containsSymbol(minRow: Int, maxRow: Int, minIndex: Int, maxIndex: Int, isRightChar: (char: Char) -> Boolean): Boolean {
    for (row in minRow..maxRow) {
        for (column in minIndex..maxIndex) {
            if (isRightChar.invoke(state[row][column])) {
                return true
            }
        }
    }
    return false
}

fun collectSymbols(minRow: Int, maxRow: Int, minIndex: Int, maxIndex: Int, isRightChar: (char: Char) -> Boolean): List<Pair<Int,Int>> {
    val result = mutableListOf<Pair<Int,Int>>()
    for (row in minRow..maxRow) {
        for (column in minIndex..maxIndex) {
            if (isRightChar.invoke(state[row][column])) {
                result.add(Pair(row, column))
            }
        }
    }
    return result
}

fun List<String>.lineLength() = this[0].length