package ru.alexsuvorov.paistewiki.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.alexsuvorov.paistewiki.core.database.model.SupportModel

@Dao
interface SupportDao {
    @Query("SELECT * FROM support_table")
    fun getSupportList(): List<SupportModel>

    @Query("SELECT * FROM support_table WHERE support_id = :supportId")
    fun getById(supportId: Int): SupportModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(supportModel: SupportModel): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(supportModel: SupportModel)

    @Delete
    fun delete(supportModel: SupportModel)
}
