# RecipeBook — Task 2: Android Application

## Project Description

RecipeBook is a simple Android application developed using Kotlin, Jetpack Compose, and Material 3. It allows users to manage their personal collection of recipes.

## Features

- View recipes with ingredients and cooking time
- Search recipes by name
- Filter recipes by category: Breakfast, Lunch, Dinner, Dessert
- Add new recipes
- Mark recipes as favorites
- Filter favorite recipes
- Delete recipes

## Technologies

- Kotlin
- Jetpack Compose
- Material 3
- ViewModel
- StateFlow

## Assignment Requirements

- **Reusable Composables:** RecipeCard, CategoryFilter, AddRecipeForm
- **Layouts:** Row, Column, Box, LazyColumn
- **UI components:** Card, Text, OutlinedTextField, Button, Checkbox
- **User interactions:** Add, delete, search, filter, favorite
- **remember:** Used for memoizing the filtered recipe list
- **rememberSaveable:** Used for search, category selection, and form inputs
- **ViewModel:** RecipeViewModel manages recipe data and application logic
- **Activity recreation:** ViewModel retains recipe data during screen rotation, while rememberSaveable restores UI input state

## How to Run

1. Open the Task 2 project folder in Android Studio.
2. Sync the Gradle project.
3. Select an Android emulator (API 26 or higher).
4. Click Run.

## Screenshots

### Recipe List

![Recipe List](screenshots/recipes.png)

### Add Recipe

![Add Recipe](screenshots/add_recipe.png)

## Notes

Recipe data is stored in memory and is not persisted after the application process is terminated.