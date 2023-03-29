package com.iqbaltio.gituser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.iqbaltio.gituser.database.FavoriteRepository
import com.iqbaltio.gituser.database.Favorite

class FavoriteViewModel(application: Application) : ViewModel() {
    private val _favoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorites() : LiveData<List<Favorite>> = _favoriteRepository.getAllFavorites()

    fun checkUserFavorite(login: String) = _favoriteRepository.checkUserFavorite(login)

    fun insert(mUser: String, mAvatar: String) {
        val mFavorite = Favorite(mAvatar, mUser)
        _favoriteRepository.insert(mFavorite)
    }
    fun delete(mUser: String) {
        _favoriteRepository.delete(mUser)
    }
}