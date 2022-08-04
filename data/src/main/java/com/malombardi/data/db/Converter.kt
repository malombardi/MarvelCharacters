package com.malombardi.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.malombardi.data.db.entities.MarvelCreatorEntity
import java.lang.reflect.Type

class Converter {
    @TypeConverter
    fun fromCreator(value: String?): List<MarvelCreatorEntity> {
        val listType: Type = object : TypeToken<List<MarvelCreatorEntity?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toCreator(list: List<MarvelCreatorEntity>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}