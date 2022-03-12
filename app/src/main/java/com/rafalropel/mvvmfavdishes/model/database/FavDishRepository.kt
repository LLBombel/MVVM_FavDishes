package com.rafalropel.mvvmfavdishes.model.database

import androidx.annotation.WorkerThread
import com.rafalropel.mvvmfavdishes.model.entities.FavDishEntity

class FavDishRepository(private val favDishDao: FavDishDao) {
    @WorkerThread
    suspend fun insertFavDishData(favDishEntity: FavDishEntity){
        favDishDao.insertFavDishDetails(favDishEntity)
    }
}