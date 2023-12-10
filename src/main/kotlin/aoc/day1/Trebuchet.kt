package aoc.day1

import aoc.dropFirst
import aoc.loadInput
import java.io.File

/**
 * https://adventofcode.com/2023/day/1
 *
 * @author Dominik Hoftych
 */

fun File.part1() = readLines()
    .map { line -> listOfNotNull(line.firstOrNull { it.isDigit() }, line.lastOrNull { it.isDigit() })
        .joinToString(separator = "")
    }
    .filter { it.isNotBlank() }
    .sumOf { it.toInt() }
    .also(::println)


fun File.part2() = readLines()
    .map { listOf(it.findFirstDigit(), it.findLastDigit()) }
    .map { "${it.first()}${it.last()}".toInt() }
    .sumOf { it }
    .also(::println)

private val spelledDigits = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

private fun String.findFirstDigit(): Int{
    fun findFirstDigit(chars: List<Char>, acc: String): Int = chars.firstOrNull()
        ?.let {
           if (it.isDigit()) {
               it.digitToInt()
           } else {
               val substring = "$acc$it"
               spelledDigits.entries
                   .firstOrNull { (spelledDigit, _) ->
                       substring.contains(spelledDigit)
                   }
                   ?.let { (_, digit) ->
                       digit
                   }
                   ?: findFirstDigit(chars.dropFirst(), substring)
           }
        }
        ?: throw IllegalStateException("String $this contains no digits")

    return findFirstDigit(toCharArray().toList(), "")
}

private fun String.findLastDigit(): Int{
    fun findLastDigit(chars: List<Char>, acc: String): Int = chars.firstOrNull()
        ?.let {
            if (it.isDigit()) {
                it.digitToInt()
            } else {
                val substring = "$acc$it"
                spelledDigits.entries
                    .firstOrNull { (spelledDigit, _) ->
                        substring.contains(spelledDigit.reversed())
                    }
                    ?.let { (_, digit) ->
                        digit
                    }
                    ?: findLastDigit(chars.dropFirst(), substring)
            }
        }
        ?: throw IllegalStateException("String $this contains no digits")

    return findLastDigit(toCharArray().reversed().toList(), "")
}

fun main() {
    with(loadInput(day = 1)) {
        part1()
        part2()
    }
}