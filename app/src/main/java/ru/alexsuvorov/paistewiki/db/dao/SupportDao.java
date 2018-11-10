package ru.alexsuvorov.paistewiki.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
