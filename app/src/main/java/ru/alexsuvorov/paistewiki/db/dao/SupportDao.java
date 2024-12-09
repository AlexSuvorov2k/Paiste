package ru.alexsuvorov.paistewiki.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.alexsuvorov.paistewiki.model.SupportModel;

@Dao
public interface SupportDao {

    @Query("SELECT * FROM support_table")
    List<SupportModel> getSupportList();

    @Query("SELECT * FROM support_table WHERE support_id = :supportId")
    SupportModel getById(int supportId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SupportModel supportModel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(SupportModel supportModel);

    @Delete
    void delete(SupportModel supportModel);
}
