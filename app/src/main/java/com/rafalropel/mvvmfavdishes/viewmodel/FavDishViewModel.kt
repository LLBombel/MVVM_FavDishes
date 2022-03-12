package com.rafalropel.mvvmfavdishes.viewmodel

import androidx.lifecycle.*
import com.rafalropel.mvvmfavdishes.model.database.FavDishRepository
import com.rafalropel.mvvmfavdishes.model.entities.FavDishEntity
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FavDishViewModel(private val repository: FavDishRepository): ViewModel() {

    fun insert(dish: FavDishEntity) = viewModelScope.launch {
        repository.insertFavDishData(dish)
    }

   val allDishesList: LiveData<List<FavDishEntity>> = repository.allDishesList.asLiveData()
}

class FavDishViewModelFactory(private val repository: FavDishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(FavDishViewModel::class.java)){
            return FavDishViewModel(repository) as T
        }
        throw IllegalArgumentException("Nieznany ViewModel")
    }

}