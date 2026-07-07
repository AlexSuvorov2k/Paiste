package ru.alexsuvorov.paistewiki.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cymbalseries")
data class CymbalSeries(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cymbalseries_id")
    var cymbalseries_id: Int = 0, //Ид серии

    @ColumnInfo(name = "cymbalseries_name")
    var cymbalName: String = "", //Название серии

    @ColumnInfo(name = "cymbalseries_subname")
    var cymbalSubName: String? = null, //Девиз серии

    @ColumnInfo(name = "cymbalseries_singleimageuri")
    var cymbalImage: String? = null, //Картинка одной тарелки

    @ColumnInfo(name = "cymbalseries_imageuri")
    var seriesImage: String? = null, //Картинка серии

    @ColumnInfo(name = "cymbalseries_description")
    var seriesDescription: String? = null, //Описание серии

    @ColumnInfo(name = "cymbalseries_isproduced")
    var seriesIsProduced: Int = 1, //В производстве?

    @ColumnInfo(name = "cymbalseries_description_application")
    var seriesDescriptionApplication: String? = null,

    @ColumnInfo(name = "cymbalseries_description_since")
    var seriesDescriptionSince: String? = null,

    @ColumnInfo(name = "cymbalseries_description_sound")
    var seriesDescriptionSound: String? = null,

    @ColumnInfo(name = "cymbalseries_description_alloy")
    var seriesDescriptionAlloy: String? = null
)
