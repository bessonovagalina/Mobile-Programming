
package com.example.recipebook

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipeViewModel : ViewModel() {

    private val _recipes = MutableStateFlow(
        listOf(
            Recipe(1, "Pancakes", "Breakfast",
                "Milk, eggs, flour, sugar", 20),
            Recipe(2, "Caesar Salad", "Lunch",
                "Chicken, lettuce, parmesan", 25),
            Recipe(3, "Pasta Carbonara", "Dinner",
                "Pasta, eggs, cheese, bacon", 30),
            Recipe(4, "Chocolate Cake", "Dessert",
                "Chocolate, flour, eggs, butter", 50)
        )
    )

    val recipes = _recipes.asStateFlow()

    fun addRecipe(
        name: String,
        category: String,
        ingredients: String
    ) {
        if (name.isBlank()) return

        val newRecipe = Recipe(
            id = (_recipes.value.maxOfOrNull { it.id } ?: 0) + 1,
            name = name.trim(),
            category = category,
            ingredients = ingredients,
            minutes = 20
        )

        _recipes.value = _recipes.value + newRecipe
    }

    fun deleteRecipe(id: Int) {
        _recipes.value = _recipes.value.filter { it.id != id }
    }

    fun toggleFavorite(id: Int) {
        _recipes.value = _recipes.value.map {
            if (it.id == id)
                it.copy(isFavorite = !it.isFavorite)
            else it
        }
    }
}
