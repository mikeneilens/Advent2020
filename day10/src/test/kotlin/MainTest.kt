import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.pow
import kotlin.time.seconds

class MainTest {
    val sample1 = listOf(16,
        10,
        15,
        5,
        1,
        11,
        7,
        19,
        6,
        12,
        4)

    val sample2 = listOf(28,
        33,
        18,
        42,
        31,
        14,
        46,
        20,
        48,
        47,
        24,
        23,
        49,
        45,
        19,
        38,
        39,
        11,
        1,
        32,
        25,
        35,
        8,
        17,
        7,
        9,
        4,
        2,
        34,
        10,
        3)
    @Test
    fun `built in jolt is 22`() {
        assertEquals(22,sample1.builtInJolt())
    }
    @Test
    fun `list of differnces of 1 is 7 and difference of 3 is 5 for sample1`() {
        val (diff1,diff3) = dayOne(sample1)
        assertEquals(7, diff1)
        assertEquals(5, diff3)
    }
    @Test
    fun `list of differnces of 1 is 22 and difference of 3 is 10 for sample2`() {
        val (diff1,diff3) = dayOne(sample2)
        assertEquals(22, diff1)
        assertEquals(10, diff3)
    }
    @Test
    fun `part one`() {
        val (diff1,diff3) = dayOne(day10Data)
        assertEquals(74, diff1)
        assertEquals(41, diff3)
        println(diff1 * diff3)
    }

    @Test
    fun `part 2 using sample1`() {
        val result = sample1.part2()
        assertEquals(8 , result)
    }

    @Test
    fun `part 2 using sample2`() {
        val result = sample2.part2()
        assertEquals(19208 , result)
    }

    @Test
    fun `part two`() {
        val result = day10Data.part2()
        assertEquals(259172170858496 , result)
    }
 }

