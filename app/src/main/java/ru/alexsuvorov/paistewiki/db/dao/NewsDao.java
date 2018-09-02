package ru.alexsuvorov.paistewiki.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
    void insert(News news);

    @Update
    void update(News news);

    @Delete
    void delete(News news);

    @Query("DELETE FROM cymbalseries")
    void deleteAll();
}