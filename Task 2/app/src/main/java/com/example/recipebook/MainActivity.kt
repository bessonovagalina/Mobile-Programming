
package com.example.recipebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel

private val Peach = Color(0xFFE99B7A)
private val Cream = Color(0xFFFFF8F1)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Peach,
                    background = Cream,
                    surface = Color.White
                )
            ) {
                RecipeBookApp()
            }
        }
    }
}

@Composable
fun RecipeBookApp(vm: RecipeViewModel = viewModel()) {
    val recipes by vm.recipes.collectAsState()
    var search by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("All") }
    var showForm by rememberSaveable { mutableStateOf(false) }
    var favoritesOnly by rememberSaveable { mutableStateOf(false) }

    val categories = listOf(
        "All", "Breakfast", "Lunch", "Dinner", "Dessert"
    )

    val filtered = remember(
        recipes, search, category, favoritesOnly
    ) {
        recipes.filter {
            it.name.contains(search, ignoreCase = true) &&
                    (category == "All" || it.category == category) &&
                    (!favoritesOnly || it.isFavorite)
        }
    }

    BackHandler(showForm) { showForm = false }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Cream
    ) {
        if (showForm) {
            AddRecipeForm(
                onSave = { name, cat, ingredients ->
                    vm.addRecipe(name, cat, ingredients)
                    showForm = false
                },
                onBack = { showForm = false }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .padding(16.dp)
            ) {
                Text(
                    "My Recipe Book",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Peach
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    label = { Text("Search recipes") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                CategoryFilter(
                    categories = categories,
                    selected = category,
                    onSelect = { category = it }
                )

                Row {
                    Checkbox(
                        checked = favoritesOnly,
                        onCheckedChange = { favoritesOnly = it }
                    )
                    Text(
                        "Favorites only",
                        modifier = Modifier.padding(top = 13.dp)
                    )
                }

                Button(
                    onClick = { showForm = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("+ Add Recipe")
                }

                Spacer(Modifier.height(8.dp))

                Text("${filtered.size} recipes")

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(filtered, key = { it.id }) { recipe ->
                        RecipeCard(
                            recipe = recipe,
                            onFavorite = {
                                vm.toggleFavorite(recipe.id)
                            },
                            onDelete = {
                                vm.deleteRecipe(recipe.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryFilter(
    categories: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    androidx.compose.foundation.lazy.LazyRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(categories.size) { index ->
            val category = categories[index]
            FilterChip(
                selected = selected == category,
                onClick = { onSelect(category) },
                label = { Text(category) }
            )
        }
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    onFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        recipe.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text("${recipe.category} · ${recipe.minutes} min")
                }

                TextButton(onClick = onFavorite) {
                    Text(
                        if (recipe.isFavorite) "♥" else "♡",
                        color = Peach,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("Ingredients: ${recipe.ingredients}")

            TextButton(onClick = onDelete) {
                Text("Delete", color = Color(0xFFB74F4F))
            }
        }
    }
}

@Composable
fun AddRecipeForm(
    onSave: (String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var ingredients by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable {
        mutableStateOf("Breakfast")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            "Add New Recipe",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Peach
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Recipe name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Text("Category")

        CategoryFilter(
            categories = listOf(
                "Breakfast", "Lunch", "Dinner", "Dessert"
            ),
            selected = category,
            onSelect = { category = it }
        )

        Button(
            onClick = { onSave(name, category, ingredients) },
            enabled = name.isNotBlank() &&
                    ingredients.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Recipe")
        }

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}
