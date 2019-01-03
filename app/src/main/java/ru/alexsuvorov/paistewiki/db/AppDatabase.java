package ru.alexsuvorov.paistewiki.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;
import ru.alexsuvorov.paistewiki.db.dao.MonthDao;
import ru.alexsuvorov.paistewiki.db.dao.NewsDao;
import ru.alexsuvorov.paistewiki.db.dao.SupportAnatomyDao;
import ru.alexsuvorov.paistewiki.db.dao.SupportDao;
import ru.alexsuvorov.paistewiki.db.framework.AssetSQLiteOpenHelperFactory;
import ru.alexsuvorov.paistewiki.model.CymbalSeries;
import ru.alexsuvorov.paistewiki.model.Month;
import ru.alexsuvorov.paistewiki.model.News;
import ru.alexsuvorov.paistewiki.model.SupportAnatomy;
import ru.alexsuvorov.paistewiki.model.SupportModel;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

@Database(entities = {CymbalSeries.class, News.class, Month.class, SupportModel.class, SupportAnatomy.class},
        version = 11, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static String DATABASE_NAME;
    private static String DATABASE_LANGUAGE;
    private static Context DATABASE_CONTEXT;

    public abstract CymbalDao cymbalDao();

    public abstract NewsDao newsDao();

    public abstract MonthDao monthDao();

    public abstract SupportDao supportDao();

    public abstract SupportAnatomyDao supportAnatomyDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = openDatabase(context);
        }
        return (INSTANCE);
    }

    public static void closeDatabase(Context context) {
        INSTANCE = null;
    }

    public static AppDatabase openDatabase(Context context) {
        AppPreferences appPreferences = new AppPreferences(context);
        DATABASE_CONTEXT = context;
        Resources res = context.getResources();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(appPreferences.getText("choosed_lang"));

        DATABASE_LANGUAGE = appPreferences.getText("choosed_lang").toUpperCase();
        DATABASE_NAME = String.format(context.getString(R.string.dbase_name), DATABASE_LANGUAGE);

        /*return Room.databaseBuilder(context,
                AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .addMigrations(MIGRATION_4_5)
                .addMigrations(MIGRATION_5_6)
                .addMigrations(MIGRATION_6_7)
                .addMigrations(MIGRATION_7_8)
                .addMigrations(MIGRATION_8_9)
                .addMigrations(MIGRATION_9_10)
                .addMigrations(MIGRATION_10_11)
                .build();*/

        RoomDatabase.Builder<AppDatabase> builder =
                Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME);

        return (builder.openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .addMigrations(MIGRATION_4_5)
                .addMigrations(MIGRATION_5_6)
                .addMigrations(MIGRATION_6_7)
                .addMigrations(MIGRATION_7_8)
                .addMigrations(MIGRATION_8_9)
                .addMigrations(MIGRATION_9_10)
                .addMigrations(MIGRATION_10_11)
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
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

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

    private static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    private static final Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //Log.d("TEST", "MIGRATE TO 9: " + database.getVersion());
            database.delete("news_month_table", null, null);
        }
    };

    private static final Migration MIGRATION_9_10 = new Migration(9, 10) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'support_table' (\n" +
                    "\t'support_id'\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t'support_title'\tTEXT,\n" +
                    "\t'support_text'\tTEXT,\n" +
                    "\t'support_image'\tTEXT\n" +
                    ");");

            database.execSQL("CREATE TABLE IF NOT EXISTS 'support_anatomy' (\n" +
                    "\t'anatomy_id'\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t'anatomy_title'\tTEXT,\n" +
                    "\t'anatomy_subtitle'\tTEXT,\n" +
                    "\t'anatomy_text'\tTEXT\n" +
                    ");");
            /*database.execSQL("COMMIT;");
            database.endTransaction();*/

/*
            String scriptId = *//*"android.resource://ru.alexsuvorov.paistewiki/assets/migrations/*//*"10_" + DATABASE_LANGUAGE + ".sql";

            Log.d("TEST", "MIGRATE TO 10: " + database.getVersion() + " NAME: " + scriptId);

            Log.d("TEST", "MIGRATE 1");
            database.execSQL(readString("10.sql"));
            Log.d("TEST", "MIGRATE 2");
            database.execSQL(readString("100_" + DATABASE_LANGUAGE + ".sql"));
            Log.d("TEST", "MIGRATE 3");
            database.execSQL(readString("10_2_" + DATABASE_LANGUAGE + ".sql"));*/
        }
    };

    private static final Migration MIGRATION_10_11 = new Migration(10, 11) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            if (DATABASE_LANGUAGE.equals("RU")) {
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (1,'Строение тарелок','У всех барабанщиков есть общее представление о том, какие бывают тарелки, но не все знают о них более детально. Наш раздел анатомии тарелок дает вам подробную информацию по их строению, различным типам и характеристикам.','@drawable/support_anatomy');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (2,'Система классификации звучания','Наша система классификации является прямым результатом кропотливых исследований. Мы очень много думали о том, как барабанщики и перкуссионисты подходят к идее выбора тарелок. Узнайте больше об эмпирическом и звуковом характере тарелок, назначении и общих критериях выбора.','@drawable/support_classification');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (3,'Выбор и тестирование','Выбор и тестирование тарелок иногда может быть трудоемкой, но приятной задачей. Это очень важный процесс в поиске конкретного вашего звука. Позвольте нам помочь вам несколькими рекомендациями по выбору идеальной тарелки Paiste для вас. Наше руководство по выбору и тестированию тарелок станет очень полезным инструментом для вас.','@drawable/support_selecting');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (4,'Использование и уход','Если вы отнесётесь к своим тарелкам с вниманием и заботой, которых они заслуживают, вы сможете наслаждаться ими в течение очень долгого времени.','@drawable/support_usage');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (5,'Советы по выбору','Несколько предложений для вас... в случае, если вы до конца не смогли определиться с выбором.','@drawable/support_proposals');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (6,'Гарантийная информация','Каждая тарелка Paiste имеет ограниченную гарантию два года c первоначальной даты покупки.','@drawable/support_warranty');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (7,'Эндорсмент','Как я могу стать эндорсером Paiste?','@drawable/support_becomeendorser');");
            } else {
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (1,'Cymbal Anatomy','All drummers have a general idea on what cymbals are but not all of us know detailed information about cymbals. Our cymbal anatomy section gives you a detailed education on the basic anatomy of a cymbal, different cymbal types, characteristics of cymbals, and drumstick basics.','@drawable/support_anatomy');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (2,'Cymbal Sound Classification System','Our classification system is a direct result of painstaking research. We thought very hard about how drummers and percussionists approach the idea of selecting cymbals. Learn more about the empirical and sound character of cymbals, the cymbal function and the general considerations for your cymbal choice.','@drawable/support_classification');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (3,'Selecting & Testing','Selecting and testing cymbals can sometimes be a time consuming yet enjoyable task. But it is a very important process in finding your particular sound. Allow us to assist you with a few guidelines in selecting and choosing the ideal Paiste cymbal for you. Our guide to selecting and testing cymbals will become a very useful tool for you.','@drawable/support_selecting');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (4,'Cymbal Usage & Care','If you treat your cymbals with the care and respect they deserve, you will be able to enjoy them for a very long time.','@drawable/support_usage');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (5,'Cymbal Set Proposals','Some suggestions for you... in case you''re overwhelmed by so many choices.','@drawable/support_proposals');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (6,'Warranty Information','Every PAISTE cymbal has a limited warranty of two years from the original date of purchase.','@drawable/support_warranty');");
                database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (7,'Artist Endorsements','How do I become a Paiste Endorser?','@drawable/support_becomeendorser');");
            }
            if (DATABASE_LANGUAGE.equals("RU")) {
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (1,'Белл','Bell находится в центре тарелки.','Его форма и размер влияют на звук всей тарелки. Он также является дополнительной зоной, поскольку создаёт другой тип звука с характерными высокими тонами и разреженными обертонами. Bell можно использовать для создания ритмических рисунков или их акцентирования. Обычно это делают наконечником палочки.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (2,'Поверхность','Поверхность тарелки создает большую часть вибрации тарелки и, следовательно, её звук.','Поверхность создает множество звуков, играя, например, в разные зоны тарелки наконечником палочки, телом палочки, щетками или колотушками. Игра на разных поверхностях тарелки также может отражаться на характере звука. Игра ближе к краю создает более низкий и более полный звук, в то время как игра рядом с беллом вызывает более высокий и резкий звук.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (3,'Край','Удар по краю тарелки создает самый насыщеный звук.','Край тарелки является самой тонкой её частью, и следует проявлять большую осторожность при игре, чтобы не повредить его.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (4,'Конусность','Конусность - это постепенное уменьшение толщины тарелки от колокола до края.','Большинство тарелок имеют  утоньшение к краю. Эта кривизна может быть сделана с помощью прессования, но мы делаем это вручную.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (5,'Кривизна или уклон','(или форма, вид)','Обычно тарелки изготавливаются посредством чеканки. Каждый удар молота сжимает и расширяет металл. Это расширение приводит к тому, что тарелка становится изогнутой вниз от белла. Форма тарелки может варьироваться от довольно плоской, что дает более низкий тон, либо более кривой, что даёт высокие тона.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (6,'Райд','Большие и обычно более толстые тарелки.','В основном они используются для воспроизведения ритмических рисунков, играя на поверхности тарелки или белле, но также их вполне можно использовать как креш. Типичный вариант использования райда - наиболее яркая часть композиции/рисунка, созданная вместе с малым и бас-барабаном. Тарелки типа райд обычно меньше \"разгоняются\", поэтому можно играть сложные ритмические паттерны, которые не будут теряться в общем звуке барабанов.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (7,'Креш','Креши обычно самые тонкие тарелки.','В основном используются для создания акцентов, ударяя их по краю. Они очень отзывчивы и имеют довольно плотный звук.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (8,'Креш-райд','Это тарелки сочетают в себе характер райд и креш тарелок.','В звуке тарелки прослушивается характер райда, но общий звук очень живой, а ударные акценты будут плотными и довольно энергичными. Также мы называем такие тарелки «Средними».');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (9,'Хай-хет','Hi-Hats это парные тарелки.','Они установлены напротив друг друга на стойке для хай-хэта. Их можно закрыть с помощью педали. Верхняя тарелка, как правило, тоньше, чтобы сделать хай-хэт более отзывчивым, чтобы играть палочками, а обе тарелки, как правило, совершенно разные по звучанию. Хетом можно играть по-разному: по закрытой поверхности для четких ритмических рисунков; по наполовину открытому- на поверхности или по краю для плотного звучания ритмических рисунков. Закрытие хай-хэта производит очень короткий клик, который используется для ритмических паттернов, обычно в контексте райда, малого и басового барабана.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (10,'Чайна или свиш','Тарелки подобного вида названы так по месту происхождения, имеют краевую область с определенной кривизной, противоположной кривизне основной поверхности.','Обычно их край изогнут вверх, так что часто тарелка установлена вверх дном, чтобы легче играть. Paiste одна из первых ввела \"моду\" на использование направленного вниз края тарелки, так что теперь также можно использовать её белл. Чайну можно использовать в качестве креша, а иногда даже в качестве райда.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (11,'Размер','Чем больше тарелка, тем больше звука она позволяет воспроизводить.','Также, чем шире тарелка той же толщины, тем ниже будет тембр её звука. Большие тарелки обычно имеют больший сустейн.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (12,'Толщина','Чем тоньше тарелка, тем более отзывчивой она будет.','Кроме того, чем толще тарелка одинаковой ширины, тем выше ее тембр. Более толстые тарелки обычно имеют больший сустейн.');\n");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (13,'Вес','Вес представляет собой комбинацию размера и толщины.','Зайдите в раздел классификации тарелок для получения большей информации.');\n");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (14,'Размер белла','Размер белла следует рассматривать(оценивать) относительно общего размера тарелки.','Как правило, тарелки с меньшим диаметром белла будут иметь более сухой звук, а тарелки с большими беллами будут более яркими.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (15,'Кривизна или уклон','Профиль или вид сбоку тарелки является результатом его кривизны.','Более высокий / закруглённый профиль тарелки говорит о более высоком тембре и плотности звука тарелки.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (16,'Наконечник','Более крупный наконечник имеет больший контакт с тарелкой и, следовательно, производит более полный звук.','Тип наконечника влияет на тон и характер звука. Для сравнения, древесный наконечник производит более теплый, более полный, мягкий и темный звук, в то время как наконечник из нейлона создает более прохладный, яркий, более сфокусированный звук.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (17,'Размер','Длина и толщина палочки сильно влияют на характер звука и громкость тарелки.','Более крупная палочка (длиннее, толще или и то, и другое) произведет более громкий звук. Более легкая палочка (короче, тоньше или и то, и другое) будет производить меньше объема и более легкий звук.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (18,'Рекомендации','Выбор палочек сугубо индивидуален.','Она должна ощущаться комфортно в ваших руках, а также производить тот звук, который вы предпочитаете. Также неплохо было бы испробовать несколько типов палочек, чтобы найти своё звучание. Вес палочки должен соответствовать размерам ваших тарелок, поэтому вы не должны играть тяжелыми палочками на маленьких или тонких тарелках. Существуют альтернативы деревянным палочкам, но мы не рекомендуем их, потому что только древесина обладает естественной гибкостью. Большинство альтернативных материалов слишком жесткие и могут привести к ослаблению или повреждению тарелок. Не используйте металлические палочки, так как они предназначены только для тренировок на резиновом педе.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (19,'Заклёпки','Заклепки добавляют приятный шуршащий звук к сустейну тарелки.','Есть несколько вещей, которые следует знать о заклепках. Чем больше заклёпок вы добавите в тарелку, тем более сухим станет сам звук тарелки. Слишком большое количество заклепок может также оказать негативное влияние на сустейн тарелки, следовательно, она может глушить сама себя, что выглядит «неестественно». Вы не должны сверлить отверстия слишком близко к краю тарелки или непосредственно при изменении кривизны в тарелке типа чайна. Большое количество заклепок или их больший размер произведут больше шипения. Заклепки, сделанные из латуни, дают более мягкое шипение. Стальные заклепки производят более яркое и острое шипение. При установке заклёпок рекомендуем обратиться к эксперту, либо написать нам.');");
            } else {
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (1,'Bell','The bell is at the center of the cymbal.','Its shape and size affects the sound of the whole cymbal, and it is a useful playing area as it produces a separate type of sound, usually a clear tone, with a dominant higher pitch and sparse overtones. The bell can be used to play rhythmic figures or to accent such figures. It is normally played with the shank of the stick.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (2,'Surface','The surface of the cymbal produces the majority of the cymbals vibration, and therefore its sound.','The surface produces a variety of sounds by playing different spots with the tip of the drumstick, with the shank of the drumstick, with brushes, or with mallets. Playing the surface in different areas can also vary the sound. Playing closer to the edge produces lower and fuller sound, while playing close to the bell produces higher and tighter sound.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (3,'Edge','Striking the edge of the cymbal produces the cymbal’s fullest sound.','The edge is the most delicate part of the cymbal and great care should be taken not to damage it in any way.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (4,'Taper','The taper is the gradual decrease in thickness from the bell to the edge.','Most cymbals are thinner toward edge. The whole curvature can also be pressed in, but we don’t do that.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (5,'Curvature or Bow','(or Form, Shape)','Traditionally cymbals are hammered. Hammering compresses the metal, but also expands it sideways. This expansion causes the cymbal to become curved downward from the bell. The shape can vary from rather flat, which gives a lower pitch, to round, which gives a higher pitch.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (6,'Ride','Ride cymbals are larger and usually thicker cymbals.','Which are mainly used for playing rhythmic figures on the surface and the bell, but they can also be crashed. Ride figures are the most prominent and colorful part of the overall rhythm created together with the bass drum and the snare. Ride cymbals usually build up less so one can play rhythmic patterns that will not get lost in the overall drum sound.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (7,'Crash','Crashes are thinner and usually smaller cymbals.','Which are mainly used for creating accents by hitting them across the edge. Crash cymbals provide cymbal sound color. Crash cymbals are usually very responsive so that the full cymbal sound can occur quickly at the desired point in the musical phrase.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (8,'Crash Ride','These are cymbals that combine ride and crash character.','Ride figures are still audible and useful but the overall sound of the cymbal is very lively, and crash accents will be full and usually quite energetic. We also refer to such cymbals as “Medium” or other designated models. Splash cymbals are very thin, small, and delicate cymbals that respond and fade quickly. Their main use is to set quick accents within the musical context.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (9,'Hi-Hat','Hi-Hats are two paired cymbals.','Which are mounted opposite each other on a hi-hat stand. They can be closed or clashed together with a foot pedal. The top cymbal is usually thinner to make the hi-hat more responsive to stick work, and the two cymbals are usually quite different in sound. Hi-Hats can be played in a variety of ways: with the stick on the closed surface for distinct rhythmic patterns, half open on the surface or across the edge for full sounding rhythmic patterns. Closing the hi-hat rapidly produces the very short chick sound, which is used for rhythmic patterns, usually in context with ride, snare, and bass drum. They can also be clashed together to produce a crash sound.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (10,'China or Swish','Chinese style cymbals, so named for their origin, feature an edge area that has a curvature opposite to the curvature of the main surface. ','The traditional edge is curved upward, so that often the cymbal is mounted upside down for easier playing. Paiste has introduced the downward turned edge to the china shape, so that the bell of the cymbal can be used well also. Chinas can be used for crashing or in larger sizes for riding. They are very versatile cymbals with a generally coarse, complex frequency mix, which is often thought of as exotic, oriental and trashy.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (11,'Size','The larger a cymbal gets, the more volume it produces.','Also, the larger a cymbal of the same thickness gets, the lower its pitch will be. Larger cymbals generally sustain longer.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (12,'Thickness','The thinner a cymbal gets, the more responsive it will be.','Also, the thicker a cymbal of the same size gets, the higher its pitch will be. Thicker cymbals generally sustain longer.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (13,'Weight','Weight is the combination of size and thickness, and thus a relative and complex relationship.','Check the classification system for more detail.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (14,'Bell Size','Bell size has to be considered relative to the overall cymbal size. ','Generally, cymbals with smaller bells will have a drier sound, and cymbals with larger bells are livelier.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (15,'Curvature or Bow (or Form, Shape)','The profile or side view of the cymbal is the result of its curvature. ','A higher/rounder profile will result in a higher overall pitch.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (16,'Tip','A larger tip has more body and contact with ','The type of the tip influences the sound color and character. In comparison, a wood tip produces a warmer, fuller, mellower, and darker sound, while a nylon tip produces a cooler, brighter, more focused sound.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (17,'Size','The length and thickness of the drumstick influence ','A heavier stick (longer, thicker or both) will produce more volume and fuller sound. A lighter stick (shorter, thinner or both) will produce less volume and lighter sound.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (18,'Stick Recommendations','The choice of drumstick is extremely personal. ','It should feel good in your hands and it should produce the type of sound you prefer. It is also a good idea to play more than one type of stick to achieve maximum variety with your cymbal sound. The weight of the stick should correspond to the sizes of your cymbals, so you should not play heavy sticks on smaller or thinner cymbals. There are alternatives to wood drumsticks, but we do not recommend them, because only wood has natural flexibility. Most alternative materials are too rigid and will cause you to weaken or break cymbals. Don’t use metal sticks on cymbals, as they are only meant for practicing purposes on a rubber pad.');");
                database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (19,'Sizzles / Rivets','Rivets add a pleasant silvery ring to the sustain of the cymbal. ','There are some things you should consider with rivets. The more sizzles you add to a cymbal, the drier the cymbal sound itself will become. Too many rivets can also have a negative effect on the cymbal’s even decay, hence it can stop itself, which appears „unnatural“. You should not drill holes too close to the edge of a cymbal or directly on the change of curvature in a china cymbal. Larger rivets or more rivets will produce more sizzle. Rivets made from brass produce a softer sizzle. Steel rivets produce a brighter, sharper sizzle. Consider having an expert install rivets or contact us for help.');");
            }
        }
    };

    public static String readString(String path) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream;
        int i;
        try {
            AssetManager mngr = DATABASE_CONTEXT.getAssets();
            inputStream = mngr.open(path);
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }/* finally {
            IOUtils.closeQuietly(inputStream);
        }*/
        //Log.d(TAG, byteArrayOutputStream.toString());
        return String.valueOf(byteArrayOutputStream);
    }
}