package ru.alexsuvorov.paistewiki.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.alexsuvorov.paistewiki.model.SupportModel

@Dao
interface SupportDao {
    @get:Query("SELECT * FROM support_table")
    val supportList: MutableList<SupportModel?>?

    @Query("SELECT * FROM support_table WHERE support_id = :supportId")
    fun getById(supportId: Int): SupportModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(supportModel: SupportModel?): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(supportModel: SupportModel?)

    @Delete
    fun delete(supportModel: SupportModel?)
}
