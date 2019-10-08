package org.alexeykalina.vfg

import com.google.gson.annotations.SerializedName

class User(val id: Int,
           @SerializedName("first_name") val name: String,
           @SerializedName("last_name") val surname: String,
           val sex: Int,
           val university: Int,
           @SerializedName("university_name") val universityName: String,
           val city: City?,
           val schools: List<School>?)

class City(val id: Int, val title: String)

class School(val id: Int, val name: String)