package com.rafalropel.mvvmfavdishes.application

import android.app.Application
import com.rafalropel.mvvmfavdishes.model.database.FavDishDatabase
import com.rafalropel.mvvmfavdishes.model.database.FavDishRepository

class FavDishApplication : Application() {

    private val database by lazy {
        FavDishDatabase.getDatabase(this@FavDishApplication)
    }
    val repository by lazy {
        FavDishRepository(database.favDishDao())
    }
}