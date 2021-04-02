package com.example.foodsample.presentation.ui.recipe_list

import java.util.*

enum class FoodCategory(val value: String) {

    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut")
}

fun fetchAllFoodCategories() = listOf(
    FoodCategory.CHICKEN,
    FoodCategory.BEEF,
    FoodCategory.SOUP,
    FoodCategory.DESSERT,
    FoodCategory.VEGETARIAN,
    FoodCategory.MILK,
    FoodCategory.VEGAN,
    FoodCategory.PIZZA,
    FoodCategory.DONUT
)

fun fetchFoodCategory(value: String): FoodCategory? {
    val valueLowerCase = value.toLowerCase(Locale.getDefault())
    val map = FoodCategory.values().map { Pair(it, it.value.toLowerCase(Locale.getDefault())) }
    return map.firstOrNull { it.second == valueLowerCase }?.first
}