import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    val sampleData = """
        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
        trh fvjkl sbzzf mxmxvkd (contains dairy)
        sqjhc fvjkl (contains soy)
        sqjhc mxmxvkd sbzzf (contains fish)
    """.trimIndent().split("\n")
    @Test
    fun `parse data`() {
        val result = sampleData.parse()
        assertEquals(4,result.size)
        assertEquals(IngredientsList(listOf("mxmxvkd", "kfcds", "sqjhc", "nhms" ),listOf("dairy", "fish")),result[0])
        val resultOfAllData = day21Data.parse()
        assertEquals(41, resultOfAllData.size)
    }

    @Test
    fun `updating allegen map using sample data`() {
        val result = sampleData.parse().updateAllergenMap(mapOf())
        assertEquals("mxmxvkd", result["dairy"])
        assertEquals("sqjhc", result["fish"])
        assertEquals("fvjkl", result["soy"])
        assertEquals(setOf("kfcds", "nhms", "trh", "sbzzf"), sampleData.parse().flatMap{it.ingredients}.toSet() - result.values.toSet() )
        val ingredientsWithNoAllergies = sampleData.parse().flatMap{it.ingredients}.toSet() - result.values.toSet()
        println(sampleData.parse().flatMap{it.ingredients}.count{ingredientsWithNoAllergies.contains(it)} )
    }
    @Test
    fun `updating allegen map using full data`() {
        val result = day21Data.parse().updateAllergenMap(mapOf())
        println(result)
        val result2 = day21Data.parse().updateAllergenMap(result)
        println(result2)
        val result3 = day21Data.parse().updateAllergenMap(result2)
        println(result3)

        val ingredientsWithNoAllergies = day21Data.parse().flatMap{it.ingredients}.toSet() - result3.values.toSet()
        val numberOfTimesIngredientsAppear = day21Data.parse().flatMap{it.ingredients}.count{ingredientsWithNoAllergies.contains(it)}
        assertEquals(2517, numberOfTimesIngredientsAppear)

        //partTwo
        val partTwo = result3.toList().sortedBy { it.first }.map{it.second}.joinToString(",")
        assertEquals("rhvbn,mmcpg,kjf,fvk,lbmt,jgtb,hcbdb,zrb", partTwo)
    }
}