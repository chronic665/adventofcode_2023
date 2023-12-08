import java.io.File
import java.util.stream.IntStream
import java.util.stream.LongStream

fun main(vararg args: String) {
//    val file = File("day_05/example_input.txt")
    val file = File("day_05/input.txt")

//    Day05().part01(file)
    Day05().part02(file)
}

data class Range(val start: Long, val end: Long, val destOffset: Long)

data class MapWrapper(val list: MutableList<Range> = mutableListOf(), var title: String? = null) {
    fun addRange(destStart: Long, sourceStart: Long, range: Long) {
        list.add(Range(sourceStart, sourceStart + range, destStart))
    }

    fun getRelatedNumber(value: Long) : Long {
        return list
            .firstOrNull { it.start <= value && it.end >= value }
            ?.let { it.destOffset + (value - it.start) }
            ?: value
    }

}

class Day05 {

    fun part01(file: File) {
        val input = file.readLines()
        val seeds = parseSeeds(input[0])
        println("Processing seeds: $seeds")

        processSeeds(seeds, input.slice(1..< input.size))
    }

    fun processSeedsList(seeds: List<List<Long>>, input: List<String>) {
        val maps = parseMaps(input)
        var min = Long.MAX_VALUE
        for (group in seeds) {
            val result = LongStream.range(group[0], group[0] + group[1])
                .map { getDistance(it, maps) }
                .min()
                .asLong
            if (result < min) min = result
        }
        println("distance: $min")
    }

    fun processSeeds(seeds: List<Long>, input: List<String>) {
        val maps = parseMaps(input)
        val result = seeds
            .map { getDistance(it, maps) }
            .min()
        println("distance: $result")
    }

    fun part02(file: File) {
        val input = file.readLines()
        val seeds = parseSeedRanges(input[0])

        println("Processing seeds: $seeds")

        processSeedsList(seeds, input.slice(1..< input.size))
    }

    fun getDistance(seed: Long, maps: List<MapWrapper>) : Long {
        return maps.fold(seed) { acc, wrapper -> wrapper.getRelatedNumber(acc) }
    }

    fun parseMaps(lines: List<String>) : List<MapWrapper> {
        val result = mutableListOf<MapWrapper>()
        var currentMap : MapWrapper? = null
        for (line in lines) {
            if (line.isEmpty()) {
                currentMap = MapWrapper()
                result.add(currentMap)
                continue
            }
            if (line.contains(":")) {
                currentMap?.title = line.split(" ")[0]
                continue
            }
            // parse map ranges
            val rangeDefinition = line.split(" ").map { it.trim() }.map { it.toLong() }
            currentMap?.addRange(rangeDefinition[0], rangeDefinition[1], rangeDefinition[2])
        }
        println("Parsed maps: $result")
        return result
    }

    private fun parseSeeds(line: String) : List<Long> {
        return line
            .split(":")[1].trim()
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { it.toLong() }
            .toList()
    }

    private fun parseSeedRanges(line: String) : List<List<Long>> {
        return line
            .split(":")[1].trim()
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { it.toLong() }
            .chunked(2)
    }
}