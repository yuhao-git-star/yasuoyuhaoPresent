package me.yasuoyuhao.yasuoyuhaopresent.model

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("humidity")
    val humidity: Double,
    @SerializedName("pressure")
    val pressure: Double,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)