package ru.alexsuvorov.paistewiki.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.alexsuvorov.paistewiki.core.database.model.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_table WHERE news_index = :month_index")
    fun getNewsByMonthIndex(month_index: Long): List<News>

    @Query("SELECT * FROM news_table")
    fun getAllNews(): List<News>

    @Query("SELECT * FROM news_table WHERE news_id = :news_id")
    fun getNewsById(news_id: Int): News?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(news: News): Long

    @Update
    fun update(news: News)

    @Delete
    fun delete(news: News)

    @Query("DELETE FROM cymbalseries")
    fun deleteAll()
}
