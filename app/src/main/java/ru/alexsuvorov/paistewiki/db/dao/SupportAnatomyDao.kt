package ru.alexsuvorov.paistewiki.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.alexsuvorov.paistewiki.model.SupportAnatomy;

@Dao
public interface SupportAnatomyDao {

    @Query("SELECT * FROM support_anatomy WHERE anatomy_id < 6")
    List<SupportAnatomy> getBasicAnatomy();

    @Query("SELECT * FROM support_anatomy WHERE anatomy_id BETWEEN 6 AND 10")
    List<SupportAnatomy> getCymbalTypes();

    @Query("SELECT * FROM support_anatomy WHERE anatomy_id BETWEEN 10 AND 15")
    List<SupportAnatomy> getCharacteristics();

    @Query("SELECT * FROM support_anatomy WHERE anatomy_id BETWEEN 15 AND 19")
    List<SupportAnatomy> getDrumstickBasics();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SupportAnatomy supportAnatomy);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(SupportAnatomy supportAnatomy);

    @Delete
    void delete(SupportAnatomy supportAnatomy);
}
