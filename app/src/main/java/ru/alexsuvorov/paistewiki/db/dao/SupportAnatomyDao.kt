package ru.alexsuvorov.paistewiki.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.alexsuvorov.paistewiki.model.SupportAnatomy

@Dao
interface SupportAnatomyDao {
    @get:Query("SELECT * FROM support_anatomy WHERE anatomy_id < 6")
    val basicAnatomy: MutableList<SupportAnatomy?>?

    @get:Query("SELECT * FROM support_anatomy WHERE anatomy_id BETWEEN 6 AND 10")
    val cymbalTypes: MutableList<SupportAnatomy?>?

    @get:Query("SELECT * FROM support_anatomy WHERE anatomy_id BETWEEN 10 AND 15")
    val characteristics: MutableList<SupportAnatomy?>?

    @get:Query("SELECT * FROM support_anatomy WHERE anatomy_id BETWEEN 15 AND 19")
    val drumstickBasics: MutableList<SupportAnatomy?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(supportAnatomy: SupportAnatomy?): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(supportAnatomy: SupportAnatomy?)

    @Delete
    fun delete(supportAnatomy: SupportAnatomy?)
}
