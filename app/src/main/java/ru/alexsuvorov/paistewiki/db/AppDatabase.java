package ru.alexsuvorov.paistewiki.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;
import ru.alexsuvorov.paistewiki.db.dao.MonthDao;
import ru.alexsuvorov.paistewiki.db.dao.NewsDao;
import ru.alexsuvorov.paistewiki.db.framework.AssetSQLiteOpenHelperFactory;
import ru.alexsuvorov.paistewiki.model.CymbalSeries;
import ru.alexsuvorov.paistewiki.model.Month;
import ru.alexsuvorov.paistewiki.model.News;

@Database(entities = {CymbalSeries.class, News.class, Month.class}, version = 7, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract CymbalDao cymbalDao();
    public abstract NewsDao newsDao();
    public abstract MonthDao monthDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = createDatabase(context);
        }
        return (INSTANCE);
    }

    private static AppDatabase createDatabase(Context context) {
        RoomDatabase.Builder<AppDatabase> builder =
                Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                        context.getString(R.string.dbase_name));
        return (builder.openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .addMigrations(MIGRATION_4_5)
                .addMigrations(MIGRATION_5_6)
                .addMigrations(MIGRATION_6_7)
                .build());
    }

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS 'cymbalseries' " +
                    "( 'cymbalseries_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " 'cymbalseries_name' TEXT NOT NULL," +
                    " 'cymbalseries_subname' TEXT," +
                    " 'cymbalseries_singleimageuri' TEXT," +
                    " 'cymbalseries_imageuri' TEXT )";
            database.execSQL(SQL_CREATE_TABLE);
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            /*String SQL_MIGRATION_3_4_pt1 = "ALTER TABLE 'cymbalseries' ADD COLUMN 'cymbalseries_description_application' TEXT";
            String SQL_MIGRATION_3_4_pt2 = "ALTER TABLE 'cymbalseries' ADD COLUMN 'cymbalseries_description_since' TEXT";
            String SQL_MIGRATION_3_4_pt3 = "ALTER TABLE 'cymbalseries' ADD COLUMN 'cymbalseries_description_sound' TEXT";
            String SQL_MIGRATION_3_4_pt4 = "ALTER TABLE 'cymbalseries' ADD COLUMN 'cymbalseries_description_alloy' TEXT";
            database.execSQL(SQL_MIGRATION_3_4_pt1);
            database.execSQL(SQL_MIGRATION_3_4_pt2);
            database.execSQL(SQL_MIGRATION_3_4_pt3);
            database.execSQL(SQL_MIGRATION_3_4_pt4);*/
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };

    private static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            String SQL_CREATE_TABLE_NEWS = "CREATE TABLE IF NOT EXISTS 'news_table' " +
                    "( 'news_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " 'news_title' TEXT," +
                    " 'news_category' TEXT," +
                    " 'news_url' TEXT," +
                    " 'news_index' INTEGER NOT NULL)";
            database.execSQL(SQL_CREATE_TABLE_NEWS);

            String SQL_CREATE_TABLE_MONTH = "CREATE TABLE IF NOT EXISTS 'news_month_table' " +
                    "( 'month_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " 'month_name' TEXT," +
                    " 'month_url' TEXT," +
                    " 'month_index' INTEGER NOT NULL)";
            database.execSQL(SQL_CREATE_TABLE_MONTH);
        }
    };

    private static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            String SQL_CREATE_NEWS_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS 'index_news_table_news_index' ON 'news_table' " +
                    "( 'news_url' )";
            database.execSQL(SQL_CREATE_NEWS_INDEX);

            String SQL_CREATE_MONTH_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS 'index_news_month_table_month_index' ON 'news_month_table' " +
                    "( 'month_index' )";
            database.execSQL(SQL_CREATE_MONTH_INDEX);
        }
    };
}