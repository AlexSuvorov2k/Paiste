package ru.alexsuvorov.paistewiki.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.alexsuvorov.paistewiki.model.Month;

@Dao
public interface MonthDao {

    /*@Query("SELECT * FROM news_table WHERE news_month_id = :month")
    List<News> getNewsByMonthName(String month);*/

    @Query("SELECT * FROM news_month_table ORDER BY month_index DESC")
    List<Month> getAllMonth();

    @Query("UPDATE news_month_table SET month_name = :monthName, month_url= :monthURL WHERE month_index =:monthIndex")
    void update(String monthName, String monthURL, int monthIndex);

    @Query("SELECT * FROM news_month_table WHERE month_id = :month_id")
    Month getMonthById(int month_id);

    @Query("SELECT * FROM news_month_table WHERE month_index = :month_index")
    Month getMonthByIndex(int month_index);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Month month);

    @Update
    void update(Month month);

    @Delete
    void delete(Month month);

    @Query("DELETE FROM cymbalseries")
    void deleteAll();
}