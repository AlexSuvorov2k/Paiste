package ru.alexsuvorov.paistewiki.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.alexsuvorov.paistewiki.model.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_table WHERE news_index = :month_index")
    fun getNewsByMonthIndex(month_index: Long): MutableList<News?>?

    @get:Query("SELECT * FROM news_table")
    val allNews: MutableList<News?>?

    @Query("SELECT * FROM news_table WHERE news_id = :news_id")
    fun getNewsById(news_id: Int): News?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(news: News?): Long

    @Update
    fun update(news: News?)

    @Delete
    fun delete(news: News?)

    @Query("DELETE FROM cymbalseries")
    fun deleteAll()
}