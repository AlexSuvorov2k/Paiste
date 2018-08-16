package ru.alexsuvorov.paistewiki.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;
import ru.alexsuvorov.paistewiki.model.CymbalSeries;

@Database(entities = {CymbalSeries.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "cymbalsbase.db";

    public abstract CymbalDao cymbalDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .addCallback(rdc)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
        public void onCreate(SupportSQLiteDatabase db) {
            String SQL_CREATE_TABLE = "CREATE TABLE `cymbalseries` " +
                    "( `cymbalseries_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `cymbalseries_name` TEXT NOT NULL," +
                    " `cymbalseries_subname` TEXT," +
                    " `cymbalseries_singleimageuri` TEXT," +
                    " `cymbalseries_imageuri` TEXT )";
            //db.execSQL(SQL_CREATE_TABLE);
            ContentValues contentValues = new ContentValues();
            contentValues.put("cymbalseries_id", "0");
            contentValues.put("cymbalseries_name", "Paiste Signature Traditionals");
            contentValues.put("cymbalseries_subname", "VINTAGE SOUND FOR JAZZ, FUSION / BEYOND");
            contentValues.put("cymbalseries_singleimageuri", "R.drawable.cymbalpic1");
            contentValues.put("cymbalseries_imageuri", "R.drawable.cymbalseriespic1");
            db.insert("cymbalseries", OnConflictStrategy.IGNORE, contentValues);
            Log.d("db create ", "table created when db created first time in  onCreate");
        }

        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            ContentValues contentValues = new ContentValues();
            /*contentValues.put("open_time", date);
            db.insert("dbusage", OnConflictStrategy.IGNORE, contentValues);
            Log.d("db open ", "adding db open date record");*/
        }
    };
}