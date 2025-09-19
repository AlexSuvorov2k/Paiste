package ru.alexsuvorov.paistewiki.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cymbalseries")
class CymbalSeries {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cymbalseries_id")
    var cymbalseries_id: Int = 0 //Ид серии

    @JvmField
    @ColumnInfo(name = "cymbalseries_name")
    var cymbalName: String? = null //Название серии

    @JvmField
    @ColumnInfo(name = "cymbalseries_subname")
    var cymbalSubName: String? = null //Девиз серии

    @JvmField
    @ColumnInfo(name = "cymbalseries_singleimageuri")
    var cymbalImage: String? = null //Картинка одной тарелки

    @JvmField
    @ColumnInfo(name = "cymbalseries_imageuri")
    var seriesImage: String? = null //Картинка серии

    @JvmField
    @ColumnInfo(name = "cymbalseries_description")
    var seriesDescription: String? = null //Описание серии

    @JvmField
    @ColumnInfo(name = "cymbalseries_isproduced")
    var seriesIsProduced: Int = 1 //В производстве?

    @JvmField
    @ColumnInfo(name = "cymbalseries_description_application")
    var seriesDescriptionApplication: String? = null

    @JvmField
    @ColumnInfo(name = "cymbalseries_description_since")
    var seriesDescriptionSince: String? = null

    @JvmField
    @ColumnInfo(name = "cymbalseries_description_sound")
    var seriesDescriptionSound: String? = null

    @JvmField
    @ColumnInfo(name = "cymbalseries_description_alloy")
    var seriesDescriptionAlloy: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val cymbalSeries = o as CymbalSeries
        if (cymbalseries_id != cymbalSeries.cymbalseries_id) return false
        return cymbalName == cymbalSeries.cymbalName
    }

    override fun hashCode(): Int {
        var result = cymbalseries_id
        result = 31 * result + (if (cymbalName != null) cymbalName.hashCode() else 0)
        return result
    }

    override fun toString(): String {
        return "CymbalSeries{" +
                "cymbalseries_id=" + cymbalseries_id +
                ", cymbalName='" + cymbalName + '\'' +
                ", cymbalSubName='" + cymbalSubName + '\'' +
                ", cymbalImage='" + cymbalImage + '\'' +
                ", seriesImage='" + seriesImage + '\'' +
                ", seriesDescription='" + seriesDescription + '\'' +
                ", seriesIsProduced=" + seriesIsProduced +
                '}'
    }

    fun getCymbalName(): String {
        return cymbalName!!
    }

    fun setCymbalName(cymbalName: String) {
        this.cymbalName = cymbalName
    }
}