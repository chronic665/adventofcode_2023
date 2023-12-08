import java.io.File

fun main(vararg args: String) {
//    val file = File("day_06/example_input.txt")
    val file = File("day_06/input.txt")
//    Day06().part01(file)
    Day06().part02(file)
}

data class Race(val time: Long, val distance: Long)

class Day06 {

    fun part01(file: File) {
        val races = parseRaces(file)
        println("found races: $races")
        races
            .map { distancesForRace(it) }
            .fold(1L) { acc, item -> acc * item}
            .also { println("Ways to win: $it") }
    }

    fun part02(file: File) {
        val race = parseRace(file)
        println("found race: $race")
        val distance = distancesForRace(race)
        println("Race distance: $distance")
    }

    fun distancesForRace(race: Race) : Long {
        val map = mutableMapOf<Long, Long>()
        // maximum distance is at race.time / 2
        var count = 0L
        for (speed in race.time downTo 0) {
            val distance = speed * (race.time - speed)
            if (distance > race.distance) {
                map[speed] = distance
                count++
            }
        }
//        count *= 2
//        println("race wins for $race: $count")
//        map.forEach { println("\t$it") }
        return count
    }

    fun parseRace(file: File) : Race {
        val lines = file.readLines()
            .map { it.split(":")[1] }
            .map { it.split("\\s+".toRegex())
                .map { s -> s.trim() }
                .filter { s -> s.isNotEmpty() }
                .joinToString("")
                .toLong()
            }
        return Race(lines[0], lines[1])
    }
    fun parseRaces(file: File) : List<Race> {
        val lines = file.readLines()
            .map { it.split(":")[1] }
            .map { it.split("\\s+".toRegex())
                .map { s -> s.trim() }
                .filter { s -> s.isNotEmpty() }
                .map { s -> s.toLong() }
            }
        val times = lines[0]
        val distances = lines[1]

        var races = mutableListOf<Race>()
        for (n in times.indices) {
            races.add(Race(times[n], distances[n]))
        }
        return races
    }
}