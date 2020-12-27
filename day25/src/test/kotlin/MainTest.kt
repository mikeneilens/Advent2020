import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `loops needed to find a key`() {
        val cardLoops = transformUntil(5764801)
        assertEquals(8, cardLoops)
        val doorLoops = transformUntil(17807724)
        assertEquals(11, doorLoops)
    }
    @Test
    fun `finding encryption key` () {
        val encryptionKey = calculateEncryptionKey(17807724, 8)
        assertEquals(14897079, encryptionKey)
        val encryptionKey2 = calculateEncryptionKey(5764801, 11)
        assertEquals(14897079, encryptionKey2)
    }
    @Test
    fun `part one`() {
        val encryptionKey = partOne(11349501,5107328)
        val encryptionKey2 = partOne(5107328,11349501)
        assertEquals(7936032, encryptionKey)
        assertEquals(7936032, encryptionKey2)
    }

}