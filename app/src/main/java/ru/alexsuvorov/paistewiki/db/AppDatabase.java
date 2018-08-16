package ru.alexsuvorov.paistewiki.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;
import ru.alexsuvorov.paistewiki.model.CymbalSeries;

@Database(entities = {CymbalSeries.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "cymbalsbase.db";
    static Context ctx;

    public abstract CymbalDao cymbalDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            ctx = context;
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
            //Context context = getDatabase(context)
            /*String SQL_CREATE_TABLE = "CREATE TABLE `cymbalseries` " +
                    "( `cymbalseries_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `cymbalseries_name` TEXT NOT NULL," +
                    " `cymbalseries_subname` TEXT," +
                    " `cymbalseries_singleimageuri` TEXT," +
                    " `cymbalseries_imageuri` TEXT )";
            db.execSQL(SQL_CREATE_TABLE);*/

            /*ContentValues contentValues = new ContentValues();
            contentValues.put("cymbalseries_id", "0");
            contentValues.put("cymbalseries_name", "Paiste Signature Traditionals");
            contentValues.put("cymbalseries_subname", "VINTAGE SOUND FOR JAZZ, FUSION / BEYOND");
            contentValues.put("cymbalseries_singleimageuri", "cymbalpic1");
            contentValues.put("cymbalseries_imageuri", "cymbalseriespic1");
            db.insert("cymbalseries", OnConflictStrategy.IGNORE, contentValues);*/
            new PopulateDbAsync(INSTANCE, ctx).execute();
            Log.d("db create ", "table created when db created first time in  onCreate");
        }

        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            ContentValues contentValues = new ContentValues();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private CymbalDao cymbalDao;
        AssetManager assetManager = ctx.getAssets();

        PopulateDbAsync(AppDatabase db, Context context) {
            cymbalDao = db.cymbalDao();
            ctx = context;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            String DB_PATH = "/data/data/ru.alexsuvorov.paistewiki/databases/";
            String DB_NAME = "cymbalsbase.db";
            try {
                Log.d("AppDatabase", "Trying copy database file");
                OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);
                byte[] buffer = new byte[1024];
                int length;
                InputStream myInput = ctx.getAssets().open("cymbalsbase.db");
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myInput.close();
                myOutput.flush();
                myOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}