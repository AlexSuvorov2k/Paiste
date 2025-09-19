package ru.alexsuvorov.paistewiki.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.alexsuvorov.paistewiki.model.Month

@Dao
interface MonthDao {
    @get:Query("SELECT * FROM news_month_table ORDER BY month_index ASC")
    val allMonth: MutableList<Month?>?

    @Query("UPDATE news_month_table SET month_name = :monthName, month_url= :monthURL WHERE month_index =:monthIndex")
    fun update(monthName: String?, monthURL: String?, monthIndex: Int)

    @get:Query("SELECT COUNT(*) FROM news_month_table")
    val count: Int

    @Query("SELECT * FROM news_month_table WHERE month_id = :month_id")
    fun getMonthById(month_id: Int): Month?

    @Query("SELECT * FROM news_month_table WHERE month_index = :month_index")
    fun getMonthByIndex(month_index: Int): Month?

    @get:Query("SELECT * FROM news_month_table ORDER BY month_id DESC LIMIT 1;")
    val lastMonthId: Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(month: Month?)

    @Update
    fun update(month: Month?)

    @Delete
    fun delete(month: Month?)

    @Query("DELETE FROM cymbalseries")
    fun deleteAll()
}