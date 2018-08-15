package ru.alexsuvorov.paistewiki.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
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
            String SQL_CREATE_TABLE = "CREATE TABLE categories" +
                    "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cat_name TEXT, " +
                    "cat_type TEXT)";
            db.execSQL(SQL_CREATE_TABLE);
            ContentValues contentValues = new ContentValues();
            contentValues.put("cat_name", "electronics");
            contentValues.put("cat_type", "commerce");
            db.insert("categories", OnConflictStrategy.IGNORE, contentValues);
            Log.d("db create ", "table created when db created first time in  onCreate");
        }

        public void onOpen(SupportSQLiteDatabase db) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("open_time", date);
            db.insert("dbusage", OnConflictStrategy.IGNORE, contentValues);
            Log.d("db open ", "adding db open date record");
        }
    };
}