package org.alexeykalina.vfg.vk

import com.google.gson.annotations.SerializedName

class User(val id: Int,
           @SerializedName("first_name") val name: String,
           @SerializedName("last_name") val surname: String,
           val sex: Int,
           val university: Int,
           @SerializedName("university_name") val universityName: String,
           val city: City?,
           val schools: List<School>?) {


    fun buildProperties(): String {
        return """{
            id: ${id},
            name: '${name}',
            sex: ${sex},
            surname: '${surname}',
            university_id: ${university},
            university_name: '${universityName}',
            city_id: ${city?.id ?: -1},
            city_name: '${city?.title ?: "no_city"}',
            school_ids: [${schools?.joinToString { it.id.toString() } ?: ""}],
            school_names: [${schools?.joinToString { "'${it.name}'" } ?: ""}] }
        """.trimIndent()
    }

    class City(val id: Int, val title: String)

    class School(val id: Int, val name: String)
}