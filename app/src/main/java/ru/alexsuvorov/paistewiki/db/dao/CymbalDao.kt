package ru.alexsuvorov.paistewiki.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.alexsuvorov.paistewiki.model.CymbalSeries

@Dao
interface CymbalDao {
    @Query("SELECT * FROM cymbalseries WHERE cymbalseries_isproduced = :cymbalseries_isProduced")
    fun getAllProduced(cymbalseries_isProduced: Int): MutableList<CymbalSeries?>

    @Query("SELECT * FROM cymbalseries WHERE cymbalseries_id = :cymbalseries_id")
    fun getById(cymbalseries_id: Int): CymbalSeries?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cymbalSeries: CymbalSeries?): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cymbalSeries: CymbalSeries?)

    @Delete
    fun delete(cymbalSeries: CymbalSeries?)

    @Query("DELETE FROM cymbalseries")
    fun deleteAll()
}
