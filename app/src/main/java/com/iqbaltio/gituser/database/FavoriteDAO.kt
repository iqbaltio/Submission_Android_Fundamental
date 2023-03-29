package com.iqbaltio.gituser.database

import androidx.lifecycle.LiveData
import androidx.room.OnConflictStrategy
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Dao

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Query("DELETE FROM Favorite WHERE login = :login")
    abstract fun delete(login: String)

    @Query("SELECT * FROM Favorite ORDER BY login ASC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM Favorite WHERE login = :login")
    fun getFavoriteByLogin(login: String): List<Favorite>
}