package ru.alexsuvorov.paistewiki.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao
import ru.alexsuvorov.paistewiki.db.dao.MonthDao
import ru.alexsuvorov.paistewiki.db.dao.NewsDao
import ru.alexsuvorov.paistewiki.db.dao.SupportAnatomyDao
import ru.alexsuvorov.paistewiki.db.dao.SupportDao
import ru.alexsuvorov.paistewiki.db.framework.AssetSQLiteOpenHelperFactory
import ru.alexsuvorov.paistewiki.model.CymbalSeries
import ru.alexsuvorov.paistewiki.model.Month
import ru.alexsuvorov.paistewiki.model.News
import ru.alexsuvorov.paistewiki.model.SupportAnatomy
import ru.alexsuvorov.paistewiki.model.SupportModel
import ru.alexsuvorov.paistewiki.tools.AppPreferences
import java.util.Locale

@Database(entities = [CymbalSeries::class, News::class, Month::class,
    SupportModel::class, SupportAnatomy::class],
    version = 11,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cymbalDao(): CymbalDao?

    abstract fun newsDao(): NewsDao?

    abstract fun monthDao(): MonthDao?

    abstract fun supportDao(): SupportDao?

    abstract fun supportAnatomyDao(): SupportAnatomyDao?

    companion object {
        private var INSTANCE: AppDatabase? = null
        private var DATABASE_NAME: String? = null
        private var DATABASE_LANGUAGE: String? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = openDatabase(context)
            }
            return (INSTANCE!!)
        }

        fun closeDatabase(context: Context?) {
            INSTANCE = null
        }

        fun openDatabase(context: Context): AppDatabase {
            val appPreferences = AppPreferences(context)
            val res = context.resources
            val conf = res.configuration
            conf.locale = Locale(appPreferences.getText("choosed_lang"))

            DATABASE_LANGUAGE = appPreferences.getText("choosed_lang").uppercase(Locale.getDefault())
            DATABASE_NAME = String.format(context.getString(R.string.dbase_name), DATABASE_LANGUAGE)

            val builder = Room.databaseBuilder<AppDatabase>(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME!!
            )

            return (builder.openHelperFactory(AssetSQLiteOpenHelperFactory())
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
                .build())
        }

        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS 'cymbalseries' " +
                        "( 'cymbalseries_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        " 'cymbalseries_name' TEXT NOT NULL," +
                        " 'cymbalseries_subname' TEXT," +
                        " 'cymbalseries_singleimageuri' TEXT," +
                        " 'cymbalseries_imageuri' TEXT )"
                database.execSQL(SQL_CREATE_TABLE)
            }
        }

        private val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        private val MIGRATION_4_5: Migration = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        private val MIGRATION_5_6: Migration = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val SQL_CREATE_TABLE_NEWS = "CREATE TABLE IF NOT EXISTS 'news_table' " +
                        "( 'news_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        " 'news_title' TEXT," +
                        " 'news_category' TEXT," +
                        " 'news_url' TEXT," +
                        " 'news_index' INTEGER NOT NULL)"
                database.execSQL(SQL_CREATE_TABLE_NEWS)

                val SQL_CREATE_TABLE_MONTH = "CREATE TABLE IF NOT EXISTS 'news_month_table' " +
                        "( 'month_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        " 'month_name' TEXT," +
                        " 'month_url' TEXT," +
                        " 'month_index' INTEGER NOT NULL)"
                database.execSQL(SQL_CREATE_TABLE_MONTH)
            }
        }

        private val MIGRATION_6_7: Migration = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val SQL_CREATE_NEWS_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS 'index_news_table_news_index' ON 'news_table' " +
                        "( 'news_url' )"
                database.execSQL(SQL_CREATE_NEWS_INDEX)

                val SQL_CREATE_MONTH_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS 'index_news_month_table_month_index' ON 'news_month_table' " +
                        "( 'month_index' )"
                database.execSQL(SQL_CREATE_MONTH_INDEX)
            }
        }

        private val MIGRATION_7_8: Migration = object : Migration(7, 8) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        private val MIGRATION_8_9: Migration = object : Migration(8, 9) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Log.d("TEST", "MIGRATE TO 9: " + database.getVersion());
                database.delete("news_month_table", null, null)
            }
        }

        private val MIGRATION_9_10: Migration = object : Migration(9, 10) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS 'support_table' (\n" +
                            "\t'support_id'\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                            "\t'support_title'\tTEXT,\n" +
                            "\t'support_text'\tTEXT,\n" +
                            "\t'support_image'\tTEXT\n" +
                            ");"
                )

                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS 'support_anatomy' (\n" +
                            "\t'anatomy_id'\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                            "\t'anatomy_title'\tTEXT,\n" +
                            "\t'anatomy_subtitle'\tTEXT,\n" +
                            "\t'anatomy_text'\tTEXT\n" +
                            ");"
                )
            }
        }

        private val MIGRATION_10_11: Migration = object : Migration(10, 11) {
            override fun migrate(database: SupportSQLiteDatabase) {
                if (DATABASE_LANGUAGE == "RU") {
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (1,'Строение тарелок','У всех барабанщиков есть общее представление о том, какие бывают тарелки, но не все знают о них более детально. Наш раздел анатомии тарелок дает вам подробную информацию по их строению, различным типам и характеристикам.','@drawable/support_anatomy');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (2,'Система классификации звучания','Наша система классификации является прямым результатом кропотливых исследований. Мы очень много думали о том, как барабанщики и перкуссионисты подходят к идее выбора тарелок. Узнайте больше об эмпирическом и звуковом характере тарелок, назначении и общих критериях выбора.','@drawable/support_classification');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (3,'Выбор и тестирование','Выбор и тестирование тарелок иногда может быть трудоемкой, но приятной задачей. Это очень важный процесс в поиске конкретного вашего звука. Позвольте нам помочь вам несколькими рекомендациями по выбору идеальной тарелки Paiste для вас. Наше руководство по выбору и тестированию тарелок станет очень полезным инструментом для вас.','@drawable/support_selecting');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (4,'Использование и уход','Если вы отнесётесь к своим тарелкам с вниманием и заботой, которых они заслуживают, вы сможете наслаждаться ими в течение очень долгого времени.','@drawable/support_usage');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (5,'Советы по выбору','Несколько предложений для вас... в случае, если вы до конца не смогли определиться с выбором.','@drawable/support_proposals');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (6,'Гарантийная информация','Каждая тарелка Paiste имеет ограниченную гарантию два года c первоначальной даты покупки.','@drawable/support_warranty');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (7,'Эндорсмент','Как я могу стать эндорсером Paiste?','@drawable/support_becomeendorser');")
                } else {
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (1,'Cymbal Anatomy','All drummers have a general idea on what cymbals are but not all of us know detailed information about cymbals. Our cymbal anatomy section gives you a detailed education on the basic anatomy of a cymbal, different cymbal types, characteristics of cymbals, and drumstick basics.','@drawable/support_anatomy');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (2,'Cymbal Sound Classification System','Our classification system is a direct result of painstaking research. We thought very hard about how drummers and percussionists approach the idea of selecting cymbals. Learn more about the empirical and sound character of cymbals, the cymbal function and the general considerations for your cymbal choice.','@drawable/support_classification');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (3,'Selecting & Testing','Selecting and testing cymbals can sometimes be a time consuming yet enjoyable task. But it is a very important process in finding your particular sound. Allow us to assist you with a few guidelines in selecting and choosing the ideal Paiste cymbal for you. Our guide to selecting and testing cymbals will become a very useful tool for you.','@drawable/support_selecting');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (4,'Cymbal Usage & Care','If you treat your cymbals with the care and respect they deserve, you will be able to enjoy them for a very long time.','@drawable/support_usage');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (5,'Cymbal Set Proposals','Some suggestions for you... in case you''re overwhelmed by so many choices.','@drawable/support_proposals');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (6,'Warranty Information','Every PAISTE cymbal has a limited warranty of two years from the original date of purchase.','@drawable/support_warranty');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_table' VALUES (7,'Artist Endorsements','How do I become a Paiste Endorser?','@drawable/support_becomeendorser');")
                }
                if (DATABASE_LANGUAGE == "RU") {
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (1,'Белл','Bell находится в центре тарелки.','Его форма и размер влияют на звук всей тарелки. Он также является дополнительной зоной, поскольку создаёт другой тип звука с характерными высокими тонами и разреженными обертонами. Bell можно использовать для создания ритмических рисунков или их акцентирования. Обычно это делают наконечником палочки.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (2,'Поверхность','Поверхность тарелки создает большую часть вибрации тарелки и, следовательно, её звук.','Поверхность создает множество звуков, играя, например, в разные зоны тарелки наконечником палочки, телом палочки, щетками или колотушками. Игра на разных поверхностях тарелки также может отражаться на характере звука. Игра ближе к краю создает более низкий и более полный звук, в то время как игра рядом с беллом вызывает более высокий и резкий звук.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (3,'Край','Удар по краю тарелки создает самый насыщеный звук.','Край тарелки является самой тонкой её частью, и следует проявлять большую осторожность при игре, чтобы не повредить его.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (4,'Конусность','Конусность - это постепенное уменьшение толщины тарелки от колокола до края.','Большинство тарелок имеют  утоньшение к краю. Эта кривизна может быть сделана с помощью прессования, но мы делаем это вручную.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (5,'Кривизна или уклон','(или форма, вид)','Обычно тарелки изготавливаются посредством чеканки. Каждый удар молота сжимает и расширяет металл. Это расширение приводит к тому, что тарелка становится изогнутой вниз от белла. Форма тарелки может варьироваться от довольно плоской, что дает более низкий тон, либо более кривой, что даёт высокие тона.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (6,'Райд','Большие и обычно более толстые тарелки.','В основном они используются для воспроизведения ритмических рисунков, играя на поверхности тарелки или белле, но также их вполне можно использовать как креш. Типичный вариант использования райда - наиболее яркая часть композиции/рисунка, созданная вместе с малым и бас-барабаном. Тарелки типа райд обычно меньше \"разгоняются\", поэтому можно играть сложные ритмические паттерны, которые не будут теряться в общем звуке барабанов.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (7,'Креш','Креши обычно самые тонкие тарелки.','В основном используются для создания акцентов, ударяя их по краю. Они очень отзывчивы и имеют довольно плотный звук.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (8,'Креш-райд','Это тарелки сочетают в себе характер райд и креш тарелок.','В звуке тарелки прослушивается характер райда, но общий звук очень живой, а ударные акценты будут плотными и довольно энергичными. Также мы называем такие тарелки «Средними».');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (9,'Хай-хет','Hi-Hats это парные тарелки.','Они установлены напротив друг друга на стойке для хай-хэта. Их можно закрыть с помощью педали. Верхняя тарелка, как правило, тоньше, чтобы сделать хай-хэт более отзывчивым, чтобы играть палочками, а обе тарелки, как правило, совершенно разные по звучанию. Хетом можно играть по-разному: по закрытой поверхности для четких ритмических рисунков; по наполовину открытому- на поверхности или по краю для плотного звучания ритмических рисунков. Закрытие хай-хэта производит очень короткий клик, который используется для ритмических паттернов, обычно в контексте райда, малого и басового барабана.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (10,'Чайна или свиш','Тарелки подобного вида названы так по месту происхождения, имеют краевую область с определенной кривизной, противоположной кривизне основной поверхности.','Обычно их край изогнут вверх, так что часто тарелка установлена вверх дном, чтобы легче играть. Paiste одна из первых ввела \"моду\" на использование направленного вниз края тарелки, так что теперь также можно использовать её белл. Чайну можно использовать в качестве креша, а иногда даже в качестве райда.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (11,'Размер','Чем больше тарелка, тем больше звука она позволяет воспроизводить.','Также, чем шире тарелка той же толщины, тем ниже будет тембр её звука. Большие тарелки обычно имеют больший сустейн.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (12,'Толщина','Чем тоньше тарелка, тем более отзывчивой она будет.','Кроме того, чем толще тарелка одинаковой ширины, тем выше ее тембр. Более толстые тарелки обычно имеют больший сустейн.');\n")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (13,'Вес','Вес представляет собой комбинацию размера и толщины.','Зайдите в раздел классификации тарелок для получения большей информации.');\n")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (14,'Размер белла','Размер белла следует рассматривать(оценивать) относительно общего размера тарелки.','Как правило, тарелки с меньшим диаметром белла будут иметь более сухой звук, а тарелки с большими беллами будут более яркими.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (15,'Кривизна или уклон','Профиль или вид сбоку тарелки является результатом его кривизны.','Более высокий / закруглённый профиль тарелки говорит о более высоком тембре и плотности звука тарелки.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (16,'Наконечник','Более крупный наконечник имеет больший контакт с тарелкой и, следовательно, производит более полный звук.','Тип наконечника влияет на тон и характер звука. Для сравнения, древесный наконечник производит более теплый, более полный, мягкий и темный звук, в то время как наконечник из нейлона создает более прохладный, яркий, более сфокусированный звук.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (17,'Размер','Длина и толщина палочки сильно влияют на характер звука и громкость тарелки.','Более крупная палочка (длиннее, толще или и то, и другое) произведет более громкий звук. Более легкая палочка (короче, тоньше или и то, и другое) будет производить меньше объема и более легкий звук.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (18,'Рекомендации','Выбор палочек сугубо индивидуален.','Она должна ощущаться комфортно в ваших руках, а также производить тот звук, который вы предпочитаете. Также неплохо было бы испробовать несколько типов палочек, чтобы найти своё звучание. Вес палочки должен соответствовать размерам ваших тарелок, поэтому вы не должны играть тяжелыми палочками на маленьких или тонких тарелках. Существуют альтернативы деревянным палочкам, но мы не рекомендуем их, потому что только древесина обладает естественной гибкостью. Большинство альтернативных материалов слишком жесткие и могут привести к ослаблению или повреждению тарелок. Не используйте металлические палочки, так как они предназначены только для тренировок на резиновом педе.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (19,'Заклёпки','Заклепки добавляют приятный шуршащий звук к сустейну тарелки.','Есть несколько вещей, которые следует знать о заклепках. Чем больше заклёпок вы добавите в тарелку, тем более сухим станет сам звук тарелки. Слишком большое количество заклепок может также оказать негативное влияние на сустейн тарелки, следовательно, она может глушить сама себя, что выглядит «неестественно». Вы не должны сверлить отверстия слишком близко к краю тарелки или непосредственно при изменении кривизны в тарелке типа чайна. Большое количество заклепок или их больший размер произведут больше шипения. Заклепки, сделанные из латуни, дают более мягкое шипение. Стальные заклепки производят более яркое и острое шипение. При установке заклёпок рекомендуем обратиться к эксперту, либо написать нам.');")
                } else {
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (1,'Bell','The bell is at the center of the cymbal.','Its shape and size affects the sound of the whole cymbal, and it is a useful playing area as it produces a separate type of sound, usually a clear tone, with a dominant higher pitch and sparse overtones. The bell can be used to play rhythmic figures or to accent such figures. It is normally played with the shank of the stick.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (2,'Surface','The surface of the cymbal produces the majority of the cymbals vibration, and therefore its sound.','The surface produces a variety of sounds by playing different spots with the tip of the drumstick, with the shank of the drumstick, with brushes, or with mallets. Playing the surface in different areas can also vary the sound. Playing closer to the edge produces lower and fuller sound, while playing close to the bell produces higher and tighter sound.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (3,'Edge','Striking the edge of the cymbal produces the cymbal’s fullest sound.','The edge is the most delicate part of the cymbal and great care should be taken not to damage it in any way.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (4,'Taper','The taper is the gradual decrease in thickness from the bell to the edge.','Most cymbals are thinner toward edge. The whole curvature can also be pressed in, but we don’t do that.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (5,'Curvature or Bow','(or Form, Shape)','Traditionally cymbals are hammered. Hammering compresses the metal, but also expands it sideways. This expansion causes the cymbal to become curved downward from the bell. The shape can vary from rather flat, which gives a lower pitch, to round, which gives a higher pitch.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (6,'Ride','Ride cymbals are larger and usually thicker cymbals.','Which are mainly used for playing rhythmic figures on the surface and the bell, but they can also be crashed. Ride figures are the most prominent and colorful part of the overall rhythm created together with the bass drum and the snare. Ride cymbals usually build up less so one can play rhythmic patterns that will not get lost in the overall drum sound.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (7,'Crash','Crashes are thinner and usually smaller cymbals.','Which are mainly used for creating accents by hitting them across the edge. Crash cymbals provide cymbal sound color. Crash cymbals are usually very responsive so that the full cymbal sound can occur quickly at the desired point in the musical phrase.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (8,'Crash Ride','These are cymbals that combine ride and crash character.','Ride figures are still audible and useful but the overall sound of the cymbal is very lively, and crash accents will be full and usually quite energetic. We also refer to such cymbals as “Medium” or other designated models. Splash cymbals are very thin, small, and delicate cymbals that respond and fade quickly. Their main use is to set quick accents within the musical context.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (9,'Hi-Hat','Hi-Hats are two paired cymbals.','Which are mounted opposite each other on a hi-hat stand. They can be closed or clashed together with a foot pedal. The top cymbal is usually thinner to make the hi-hat more responsive to stick work, and the two cymbals are usually quite different in sound. Hi-Hats can be played in a variety of ways: with the stick on the closed surface for distinct rhythmic patterns, half open on the surface or across the edge for full sounding rhythmic patterns. Closing the hi-hat rapidly produces the very short chick sound, which is used for rhythmic patterns, usually in context with ride, snare, and bass drum. They can also be clashed together to produce a crash sound.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (10,'China or Swish','Chinese style cymbals, so named for their origin, feature an edge area that has a curvature opposite to the curvature of the main surface. ','The traditional edge is curved upward, so that often the cymbal is mounted upside down for easier playing. Paiste has introduced the downward turned edge to the china shape, so that the bell of the cymbal can be used well also. Chinas can be used for crashing or in larger sizes for riding. They are very versatile cymbals with a generally coarse, complex frequency mix, which is often thought of as exotic, oriental and trashy.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (11,'Size','The larger a cymbal gets, the more volume it produces.','Also, the larger a cymbal of the same thickness gets, the lower its pitch will be. Larger cymbals generally sustain longer.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (12,'Thickness','The thinner a cymbal gets, the more responsive it will be.','Also, the thicker a cymbal of the same size gets, the higher its pitch will be. Thicker cymbals generally sustain longer.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (13,'Weight','Weight is the combination of size and thickness, and thus a relative and complex relationship.','Check the classification system for more detail.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (14,'Bell Size','Bell size has to be considered relative to the overall cymbal size. ','Generally, cymbals with smaller bells will have a drier sound, and cymbals with larger bells are livelier.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (15,'Curvature or Bow (or Form, Shape)','The profile or side view of the cymbal is the result of its curvature. ','A higher/rounder profile will result in a higher overall pitch.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (16,'Tip','A larger tip has more body and contact with ','The type of the tip influences the sound color and character. In comparison, a wood tip produces a warmer, fuller, mellower, and darker sound, while a nylon tip produces a cooler, brighter, more focused sound.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (17,'Size','The length and thickness of the drumstick influence ','A heavier stick (longer, thicker or both) will produce more volume and fuller sound. A lighter stick (shorter, thinner or both) will produce less volume and lighter sound.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (18,'Stick Recommendations','The choice of drumstick is extremely personal. ','It should feel good in your hands and it should produce the type of sound you prefer. It is also a good idea to play more than one type of stick to achieve maximum variety with your cymbal sound. The weight of the stick should correspond to the sizes of your cymbals, so you should not play heavy sticks on smaller or thinner cymbals. There are alternatives to wood drumsticks, but we do not recommend them, because only wood has natural flexibility. Most alternative materials are too rigid and will cause you to weaken or break cymbals. Don’t use metal sticks on cymbals, as they are only meant for practicing purposes on a rubber pad.');")
                    database.execSQL("INSERT OR REPLACE INTO 'support_anatomy' VALUES (19,'Sizzles / Rivets','Rivets add a pleasant silvery ring to the sustain of the cymbal. ','There are some things you should consider with rivets. The more sizzles you add to a cymbal, the drier the cymbal sound itself will become. Too many rivets can also have a negative effect on the cymbal’s even decay, hence it can stop itself, which appears „unnatural“. You should not drill holes too close to the edge of a cymbal or directly on the change of curvature in a china cymbal. Larger rivets or more rivets will produce more sizzle. Rivets made from brass produce a softer sizzle. Steel rivets produce a brighter, sharper sizzle. Consider having an expert install rivets or contact us for help.');")
                }
            }
        }

        private val MIGRATION_11_12: Migration = object : Migration(11, 12) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS 'cymbal_classification' (\n" +
                            "\t'cy_classification_id'\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                            "\t'cy_classification_text_type'\tINTEGER,\n" +
                            "\t'cy_classification_text_section'\tINTEGER,\n" +
                            "\t'cy_classification_text_content'\tTEXT\n" +
                            ");"
                )

                // Type: font, 1 of 5 available
                // Section: 1 of 3 available
                if (DATABASE_LANGUAGE != "RU") {
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (1,1,1,'Introduction');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (2,2,1,'When we normally think about and hear cymbal sound, we take a very intuitive approach. We either like what we hear, or we like it but feel it might not work for us, or we don’t like it. This approach works, because in the end we will only acquire and keep a cymbal in our set if we like it and it works.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (3,5,1,'In our experience, and through talking with a lot of cymbal players, this can often be a very tedious and frustrating process. Often it involves going through piles of cymbals until we feel the magic “click”. Too often it can also lead to purchases purely based on liking something in the store context but finding out later it does not work so well in our musical context. Finally, the choices today are overwhelming. There are seemingly too many cymbals and not enough time.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (4,5,1,'At Paiste, we thought very hard about this process and how drummers and percussionists approach and select cymbals. On the one hand we have the experience of decades of work with top artists in designing new cymbals and helping them select their sets. On the other hand we listen to the drummers and percussionists “in the street”, who may sometimes be more knowledgeable than a seasoned pro and may sometimes not yet be as clear, aware and specific about their sound ideas. Invariably, we found that the process involves these simple steps you should take to arrive at a satisfying decision:');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (5,5,1,'form an idea of what you are looking for; this idea could be a combination of personal sound feeling (clear, dark, lively, dirty, subtle, powerful, the list goes on forever), musical context (style, instrumentation, feeling), application setting (volume, personal playing style, preferences of fellow musicians), but can also incorporate anything else you want');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (6,5,1,'match this idea with cymbal literature in a pre-selection process; identify models that could fulfill your personal sound ideas');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (7,5,1,'verbalize your ideas to manufacturer representatives and/or music store personnel to receive advice and guidance; describe your sound ideas using a handful of relatively clear and recognizable parameters');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (8,5,1,'narrow down the field of selection to a manageable group of models; identify and search out models in order to play them and experience their sound and function. If possible, have a friend or music store personnel play the cymbal and listen from a distance');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (9,5,1,'decide on a particular model and assure yourself of its usefulness for your musical purpose by comparing your sound idea to the live experience with the cymbal using the sound and function parameters as a personal check list');")

                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (10,1,2,'Classification System');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (11,2,2,'In designing and analyzing cymbals, we have found the various parameters of cymbal sound can be grouped into three logical groups:');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (12,4,2,'Empirical Characteristics\n');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (13,5,2,'These can be experienced and verified physically and include the SIZE and THICKNESS, which should be considered together as the WEIGHT (thickness proportional to the size of the cymbal); and the VOLUME (range where the cymbal performs well). The FORM of the cmybal gives you an idea on what pitch it performs.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (14,4,2,'Sound Character');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (15,5,2,'These are the frequencies and harmonics produced by a cymbal which result in a very personal sound feeling and experience and are consequently extremely hard to categorize; they include COLOR (overall dominance of higher or lower frequencies), the RANGE (presence of lowest to highest audible frequencies) and the MIX (density of audible frequencies)');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (16,4,2,'Function');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (17,5,2,'This area addresses how the cymbal reacts to what you do to it with a drum stick; it includes the ATTACK or stick sound (the immediate sound initially heard when striking the cymbal), the RESPONSE INTENSITY (the potency of frequencies developing from the vibration of the cymbal as a result of the stroke), the SUSTAIN (the audible length of the sound vibration), and type specific functional characteristics, such as the BELL CHARACTER in Rides, and the CHICK SOUND, the interaction of the two cymbals in a Hi-Hat; finally there is the FEEL of the cymbal (the feeling you experience in your hands and with the stick as you hit the cymbal or play rhythmic figures on it)');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (18,4,2,'Sound Descriptions');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (19,5,2,'In describing our cymbals, we have found it most useful to present the empirical and functional characteristics as bullet points, while we have presented the sound characteristics in descriptive words. Where appropriate, we have also added notes about the feel of the cymbal, and its particular usefulness, such as hand playing and other percussive techniques, as well as suggested musical settings. ');")

                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (20,3,2,'Empirical Character');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (21,4,2,'Size and Thickness: Small to Large and Thin to Thick. These are the most basic characteristics. They determine the overall pitch and sustain of the cymbal. In simple terms, the larger a cymbal of a certain type gets, the lower its pitch will be and the thicker a cymbal of a certain size gets the higher its pitch will be. Also, the larger and/or thicker cymbals get, the longer the sustain will be. However, these characteristics do not exist by themselves, but will always be relative to each other, so they can only be used as a general starting point in thinking about cymbal sound.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (22,5,2,'These can be experienced and verified physically and include the SIZE and THICKNESS, which should be considered together as the WEIGHT (thickness proportional to the size of the cymbal); and the VOLUME (range where the cymbal performs well). The FORM of the cmybal gives you an idea on what pitch it performs.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (23,4,2,'Weight – Range: Extra Thin to Extra Heavy.');")
                    database.execSQL(
                        "INSERT OR REPLACE INTO 'cymbal_classification' VALUES (24,5,2,'This is defined as thickness proportional to size. It is important to understand that weight is a very relative concept, as it results from a combination of thickness and size. In general terms, as a cymbal gets thicker, the volume will be louder, the sound color will be brighter, the frequency range will be narrower, the mix more complex, the attack sound will become more pronounced, the response more lively, and the sustain longer. As a cymbal gets larger, the volume will be louder, the sound color will be darker, the range will be wider, the mix more complex, the attack or stick sound may go either way, the response will be more lively, and the sustain will be longer. The various combinations of weight and size may now balance out, reinforce, or negate these various tendencies. The best way to understand this interaction is to consider two extreme, diametrically opposite models. A very thick, very small cymbal will have medium volume, a very bright color, a narrow range, a clean mix, a very pronounced attack, a dry response, but a long sustain (because more mass almost always overrides size). A very thin, very large cymbal, will have medium volume, a very dark color, a wider range, a complex mix, a very washy attack, the response will be less lively, and the sustain will be long.');"
                    )
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (25,4,2,'Volume – Range: Very Soft to Very Loud.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (26,5,2,'This refers to the useful volume range of the cymbal. On the low end of the spectrum we consider how softly we can play the cymbal before its character falls apart. This is best illustrated considering a huge, thick, powerful Ride cymbal where at some point of softer playing not enough force is applied to properly excite the mass of the cymbal so it cannot unfold its full character. On the high end of the spectrum we consider how strongly we can play the cymbal without overplaying it. Consider a medium size thin cymbal that will be played heavily in a loud setting. At some point of heavy hitting, not only will the cymbal loose its intended definition, but you also run the risk of destroying an otherwise perfectly well made and sturdy cymbal.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (27,5,2,'Form – Pitch: Low to High Pitch.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (28,5,2,'This is defined as pitch of the cymbal. The shape can vary from rather flat, which gives a lower pitch, to round, which gives a higher pitch.');")

                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (29,3,2,'Sound Character');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (30,4,2,'Sound Color – Range: Very Bright to Very Dark.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (31,5,2,'This refers to the overall relative strength of higher to lower frequencies. It is important to understand that in almost all cymbals, the whole range of very low to extremely high frequencies is more or less present. It is the intensity of portions of the spectrum that give the cymbal its overall feeling of sound color. Generally speaking, higher frequencies are experienced as brighter, lower frequencies as darker. Consider also that the frequency range will subjectively alter the overall color feeling.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (32,4,2,'Frequency Range – Range: Very Narrow – Very Wide.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (33,5,2,'Here we are considering the upper and lower frequency limits of all the frequencies present. In general, cymbals consist of roughly three layers of sound. On the lower end (“undertones”) we have the overall “gong sound” or basic pitch of the cymbal, which could be best isolated playing the cymbal with a relatively soft mallet, and on the upper end (“overtones”) we have “silver sound” or shimmer, which could best be isolated by hitting the cymbal parallel to its surface with the shaft of the drum stick. How wide the frequency range of overall cymbal sound is has important functional and character effects. Wider range results in looser, bigger sound, narrower range in more focused, together sound. Hi-Hats, because they are pairs of two different cymbals, have a special relationship: greater weight difference between the top and bottom cymbal tends to produce a wider range.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (34,4,2,'Frequency Mix – Range: Very clean (delicate) – Very Complex (rough).');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (35,5,2,'Here we are considering the density of the cymbal sound, or the relative presence of the center section of frequencies. This is the middle layer of the overall cymbal sound, also referred to as its “voice”. Crashing the cymbal across its edge with a medium strength stroke will highlight the center frequencies. A relative absence of middle frequencies will be perceived as a clean or clear (“voiceless”) cymbal sound because the upper and lower layers co-exist without too much interference, while a relative abundance of center frequencies tends to combine and intricately mix all the frequencies in the cymbal which will then be perceived as complex (and in a darker cymbal as dirty). ');")

                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (36,3,2,'Function');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (37,4,2,'Attack / Stick Sound – Range: Pronounced/Pingy to Spread/Washy.');")
                    database.execSQL(
                        "INSERT OR REPLACE INTO 'cymbal_classification' VALUES (38,5,2,'This refers to the initial sound immediately following the stroke with the drumstick tip on the surface (“ping”). This sound also incorporates the wood sound of the drumstick hitting the cymbal, which ever so slightly transmits into the cymbal sound. The sound from the tip of the drumstick has the most significance when playing rhythmic figures on a Ride cymbal. It can either be very pronounced or pingy, where the “pings” are clearly separate and distinct from the overall cymbal sound, or spread or washy, where the “pings” are cushioned or even buried in the overall cymbal sound, or in between, providing a balance between rhythmic articulation and response sound (“wash”). Hi-Hats, when played open almost always have a washy sound (unless the top cymbal is quite thick), while rhythmic, articulate figures are achieved by playing the closed Hi-Hat with the tip on the surface or with the shaft across the edge. A balance between the two is achieved by breaking the open, washy response sound through open-closed playing. The tip sound from the stick on a Crash or Splash cymbal is always washy, and usually they are “crashed” with the shaft across the edge anyway. A China or Swish cymbal can be played either way, but the tip sound from the stick will always tend to be washy.');"
                    )
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (39,4,2,'Response Intensity – Range: Dry – Lively.');")
                    database.execSQL(
                        "INSERT OR REPLACE INTO 'cymbal_classification' VALUES (40,5,2,'This refers to the frequencies and harmonics that develop following the stroke that sets the cymbal into vibration. The response sound is the intensity, complexity and speed with which the cymbal opens up as a result of a stroke. There can be less response sound resulting in a dry feeling, or more response sound resulting in a wet or lively feeling. Crash, Splash and China cymbals always tend to be lively, while Ride cymbals can go either way (when played with the tip because their crash sound will almost always be lively). In a Ride cymbal a relative dry character results in a focused, controllable sound, which works well for rhythmic articulation, while a wet or lively character tends to build up a “wash” of sound within which the rhythmic articulation coexists but is still very audible. All Hi-Hats tend to be lively played open, because the top cymbal usually has crash character. Hi-Hats with less combined top/bottom weight tend to be drier, faster, and more responsive, and will allow fast, articulate playing. Hi-Hats with thinner top cymbals will have a lively response, and will be more dynamic and controllable in open-closed playing.');"
                    )
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (41,4,2,'Sustain – Range: Short – Long.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (42,5,2,'This refers to the length in time a cymbal can still be heard after striking it. In all cymbals, including Rides, the larger and thicker they are, the longer their sustain will be. A longer sustain makes a cymbal more useful for creating “sound walls” that fill the overall musical “soundscape” (but you can also achieve the same effect by fast rhythmic playing). The smaller and thinner a cymbal is, the shorter and more useful it will be for quick accents. A cymbal with longer sustain but very dry character will subjectively feel shorter.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (43,4,2,'Bell Character – Range: Integrated – Separated.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (44,5,2,'This refers to whether the bell sound of the cymbal is clearly separated from the rest of the cymbal. In a cymbal with an integrated bell sound, the whole cymbal will also respond easily when you strike just the bell. You can also think of the bell sound as a huge ping sound as in a Ride that’s either pingy or washy. Only Rides and very few China cymbals tend to have separated bell sounds.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (45,4,2,'Hi-Hat Chick Sound – Range: Soft/Tight to Sharp/Pronounced.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (46,5,2,'This refers to the sound of two Hi-Hat cymbals clashing together. This sound is always very short, but can differ very much by ranging from soft, precise, tight to sharp, meaty, pronounced. This character attribute is important to consider in the overall volume setting and style of music played.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (47,4,2,'Feel – Range: Soft - Heavy.');")
                    database.execSQL(
                        "INSERT OR REPLACE INTO 'cymbal_classification' VALUES (48,5,2,'This can be a very important factor, because it is the immediate physical sensation you have interacting with the cymbal, as the response or resistance of the cymbal travels through the stick into your hands. A cymbal with a soft feel has little resistance, and is perceived as buttery or giving. An even feel is neutral, or not particularly giving or resistant. A heavy feel offers noticeable resistance; you can really feel the mass of the cymbal. Feel is a very personal preference; Jazz drummers tend to prefer a soft, buttery feel in a ride while Rock drummers may enjoy the heavy presence of a massive cymbal. In general, a cymbal with less weight will tend towards a softer feel. The relative rigidity of the cymbal surface (which you can test by carefully bending it in your hands) will also determine this parameter, as a more rigid cymbal will feel heavier. Consider that a softer cymbal can possibly absorb the shock of your stroke better than a thicker cymbal. Longevity of each cymbal has to do with the ultimative „absorb-ability“. ');"
                    )

                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (49,1,3,'General Considerations');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (50,2,3,'When considering all the parameters presented for the model, it is important to keep in mind that some general factors can also significantly influence your choice:');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (51,5,3,'Size of the cymbal within a particular model. The characteristics presented are most applicable to the most usual size within a model. This would be 10” for Splash, 14” for Hi-Hat, 16” for Crash, 18” for China, and 20” for Ride. The effects of the size dimension discussed in the size and weight sections will change as you consider different sizes within a given model.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (52,5,3,'Type of stick and playing style. The characteristics presented for the various models assume a medium size and weight wood tip stick and an average playing style. Your choice of stick and your playing style can make a big difference in how a cymbal will actually behave and sound.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (53,5,3,'Musical setting within which cymbals will be played. This is usually very straightforward. Louder music requires bigger, heavier cymbals and softer music requires thinner, smaller cymbals. On the other hand, unusual situations could dramatically alter cymbal choice. Consider these examples: playing a Power Ride in a ballad, because you will be performing in huge arenas in front of 50,000 people; playing ride figures very softly on a small thin crash in the studio because it sounds right and the engineer can bring it up in the mix.');")
                    database.execSQL("INSERT OR REPLACE INTO 'cymbal_classification' VALUES (54,5,3,'Overall character of the cymbal line. Each cymbal line has a different musical context within which it was developed. For instance, consider the 2002 which was created during the late 60’s and 70’s when Beat Music and Rock Music history was being written and electronic amplification changed the overall sound of music, or the Traditionals, which recreate cymbal sound appropriate for the various largely acoustic forms of Jazz in the 40’s, 50’s and 60’s, or the Signature, whose development was strongly influenced by the sound property of the alloy used but otherwise covers many musical styles and ages. It is therefore helpful to listen to several lines to get a feeling for the overall sound character when considering the models.');")
                }
            }
        }
    }
}