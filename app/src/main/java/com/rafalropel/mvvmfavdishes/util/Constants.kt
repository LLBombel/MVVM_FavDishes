package com.rafalropel.mvvmfavdishes.util

object Constants {
    const val DISH_TYPE: String = "DishType"
    const val DISH_CATEGORY: String = "DishCategory"
    const val DISH_COOKING_TIME: String = "DishCookingTime"
    const val DISH_IMAGE_SOURCE_LOCAL: String = "local"
    const val DISH_IMAGE_SOURCE_ONLINE: String = "online"


    fun dishTypes(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Śniadanie")
        list.add("Lunch")
        list.add("Obiad")
        list.add("Przekąska")
        list.add("Podwieczorek")
        list.add("Deser")
        list.add("Kolacja")
        list.add("Inne")

        return list
    }

    fun dishCategory(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Pizza")
        list.add("Pieczywo")
        list.add("Burger")
        list.add("Kurczak")
        list.add("Drink")
        list.add("Sok")
        list.add("Kawa/herbata")
        list.add("Inne")
        return list
    }

    fun dishCookTime(): ArrayList<String>{
        val list = ArrayList<String>()
        list.add("5-10")
        list.add("10-20")
        list.add("20-40")
        list.add("40-60")
        list.add("60-90")
        list.add("90-120")
        list.add("120-150")
        list.add("150-180")
        return list
    }
}