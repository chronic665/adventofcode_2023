println("Day 01 challenge in Julia!")

global numbersInLetters = ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"]
global numbersReversed = ["eno", "owt", "eerht", "ruof", "evif", "xis", "neves", "thgie", "enin"]

function linevalue(first, last)
    println(string("\tcalculating:", first, " + ", last))
    return string(first, last)
end

function isnumeric(s)
    return tryparse(Int64, string(s)) !== nothing
end

# TODO should use a dictionary for the structures
function interpretWordAsNumeric(word)
    if word == "one" || word == "eno"
        return 1
    elseif word == "two" || word == "owt"
        return 2
    elseif word == "three" || word == "eerht"
        return 3
    elseif word == "four" || word == "ruof"
        return 4
    elseif word == "five" || word == "evif"
        return 5
    elseif word == "six" || word == "xis"
        return 6
    elseif word == "seven" || word == "neves"
        return 7
    elseif word == "eight" || word == "thgie"
        return 8
    elseif word == "nine" || word == "enin"
        return 9
    end
    println(string("error: unexpected word", word))
end

function findnumeric(str, numbersAsLetters)
    for (idx,s) in enumerate(str)
        if isnumeric(s)
            return s
        else
            for word in numbersAsLetters
                # sub = chop(string, head=idx, tail=length(word))
                if idx + length(word) > length(str)
                    continue
                end
                sub = str[idx:idx + length(word) - 1]
                println(string("\tcomparing word ", word, " with substring ", sub))
                if sub == word
                    println("\t->MATCH!!")
                    return interpretWordAsNumeric(word)
                end
            end
        end
    end
end

function calculatelinevalue(line)
    println(line)
    first = findnumeric(line, numbersInLetters)
    last = findnumeric(reverse(line), numbersReversed)
    lineresult = linevalue(first, last)
    println("\t", lineresult)
    return lineresult
end

function getTotal(lines)
    total = 0
    for line in lines
        lineresult = calculatelinevalue(line)
        total += parse(Int64, lineresult)
    end
    return total
end

lines = readlines("input.txt")
total = getTotal(lines)

println(string("total: ", total))
