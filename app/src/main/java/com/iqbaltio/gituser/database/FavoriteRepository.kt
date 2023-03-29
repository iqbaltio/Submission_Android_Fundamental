package com.iqbaltio.gituser.database

import android.app.Application
import com.iqbaltio.gituser.database.Favorite
import com.iqbaltio.gituser.database.FavoriteDAO
import com.iqbaltio.gituser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites() = mFavoriteDao.getAllFavorites()
    fun checkUserFavorite(login: String) = mFavoriteDao.getFavoriteByLogin(login)

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }
    fun delete(mfavorite: String) {
        executorService.execute { mFavoriteDao.delete(mfavorite) }
    }
}