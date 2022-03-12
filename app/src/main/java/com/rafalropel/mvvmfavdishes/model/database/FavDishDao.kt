package com.rafalropel.mvvmfavdishes.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.rafalropel.mvvmfavdishes.model.entities.FavDishEntity


@Dao
interface FavDishDao {

    @Insert
    suspend fun insertFavDishDetails(favDish: FavDishEntity)



}