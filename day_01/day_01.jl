println("Day 01 challenge in Julia!")

function linevalue(first, last)
    println(string("\tcalculating:", first, " + ", last))
    return string(first, last)
end

function isnumeric(s)
    return tryparse(Int64, string(s)) !== nothing
end

function findnumeric(string)
    for s in string
        if isnumeric(s)
            return s
        end
    end
end

function calculatelinevalue(line)
    println(line)
    first = findnumeric(line)
    last = findnumeric(reverse(line))
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