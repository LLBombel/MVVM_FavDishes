package com.rafalropel.mvvmfavdishes.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rafalropel.mvvmfavdishes.model.entities.FavDishEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavDishDao {

    @Insert
    suspend fun insertFavDishDetails(favDish: FavDishEntity)

    @Query("SELECT * FROM FAV_DISH_TABLE ORDER BY ID")
    fun getAllDishes(): Flow<List<FavDishEntity>>



}