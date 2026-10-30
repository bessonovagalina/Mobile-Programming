
package com.example.recipebook

data class Recipe(
    val id: Int,
    val name: String,
    val category: String,
    val ingredients: String,
    val minutes: Int,
    val isFavorite: Boolean = false
)
