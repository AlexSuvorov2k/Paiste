package ru.alexsuvorov.paistewiki.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.alexsuvorov.paistewiki.model.CymbalSeries;

@Dao
public interface CymbalDao {

    @Query("SELECT * FROM cymbalseries WHERE cymbalseries_isproduced = :cymbalseries_isProduced")
    List<CymbalSeries> getAllProduced(int cymbalseries_isProduced);

    @Query("SELECT * FROM cymbalseries WHERE cymbalseries_id = :cymbalseries_id")
    CymbalSeries getById(int cymbalseries_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CymbalSeries cymbalSeries);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(CymbalSeries cymbalSeries);

    @Delete
    void delete(CymbalSeries cymbalSeries);

    @Query("DELETE FROM cymbalseries")
    void deleteAll();
}
