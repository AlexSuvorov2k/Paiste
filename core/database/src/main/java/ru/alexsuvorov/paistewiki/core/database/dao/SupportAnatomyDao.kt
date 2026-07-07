package ru.alexsuvorov.paistewiki.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.alexsuvorov.paistewiki.core.database.model.SupportAnatomy

@Dao
interface SupportAnatomyDao {
    @Query("SELECT * FROM support_anatomy WHERE anatomy_id < 6")
    fun getBasicAnatomy(): List<SupportAnatomy>

    @Query("SELECT * FROM support_anatomy WHERE anatomy_id BETWEEN 6 AND 10")
    fun getCymbalTypes(): List<SupportAnatomy>

    @Query("SELECT * FROM support_anatomy WHERE anatomy_id BETWEEN 10 AND 15")
    fun getCharacteristics(): List<SupportAnatomy>

    @Query("SELECT * FROM support_anatomy WHERE anatomy_id BETWEEN 15 AND 19")
    fun getDrumstickBasics(): List<SupportAnatomy>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(supportAnatomy: SupportAnatomy): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(supportAnatomy: SupportAnatomy)

    @Delete
    fun delete(supportAnatomy: SupportAnatomy)
}
