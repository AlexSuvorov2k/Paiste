package ru.alexsuvorov.paistewiki.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.alexsuvorov.paistewiki.model.News;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news_table WHERE news_index = :month_index")
    List<News> getNewsByMonthIndex(long month_index);

    @Query("SELECT * FROM news_table")
    List<News> getAllNews();

    @Query("SELECT * FROM news_table WHERE news_id = :news_id")
    News getNewsById(int news_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(News news);

    @Update
    void update(News news);

    @Delete
    void delete(News news);

    @Query("DELETE FROM cymbalseries")
    void deleteAll();
}