

data class IngredientsList(
    val ingredients:List<String>,
    val allergens:List<String>
)

fun List<String>.parse():List<IngredientsList> =  map{ line ->
        val ingredients = line.split(" (")[0].split(" ")
        val allergens = line.split(" (contains ")[1].removeSuffix(")").split(", ")
        IngredientsList(ingredients, allergens)
}

fun List<IngredientsList>.onlyTheIngredientsAlwaysWith(allergen:String = "dairy"): List<String> {
    val ingredientsWithAllergen = filter{it.allergens.contains(allergen)}
    val noOfTimesEachIngredientOccurs = ingredientsWithAllergen.flatMap { it.ingredients }.groupingBy { it }.eachCount()
    return noOfTimesEachIngredientOccurs.filter{(k,v) -> v == ingredientsWithAllergen.size}.map{it.key}
}

fun List<IngredientsList>.updateAllergenMap(foundAllergens:Map<String, String>):Map<String, String> {
    val allergenMap = foundAllergens.toMutableMap()
    val allergens = flatMap { it.allergens }.filter{!allergenMap.keys.contains(it)}.distinct()
    allergens.forEach { allergen ->
        val ingredientsUsedWithAllegen = onlyTheIngredientsAlwaysWith(allergen).filter{!allergenMap.values.contains(it)}
        if (ingredientsUsedWithAllegen.size == 1) {
            allergenMap[allergen] = ingredientsUsedWithAllegen.first()
        }
    }
    return allergenMap
}