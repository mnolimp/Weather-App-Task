package com.mnolimp.weatherforecastprovider

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors

private val executor = Executors.newSingleThreadExecutor()
private var location : String = "Unknown"
private var weatherTemperature : String = "Zero"
private var weatherFeelsLike : String = "Zero"
private var weatherMaxTemperature : String = "Zero"
private var weatherMinTemperature : String = "Zero"
private var weatherType : String = "Unknown"
private var weatherCloudness : Int = 0
private var weatherWindSpeed : Int = 0
private val citiesList = arrayListOf(
    arrayListOf("Omsk", "55.00", "73.24"),
    arrayListOf("Moscow", "55.45", "37.37"),
    arrayListOf("Saint-Petersburg", "59.57", "30.19"),
    arrayListOf("Novosibirsk", "55.01", "82.55"),
    arrayListOf("Ekaterinburg", "56.50", "60.35"),
    arrayListOf("Vladivostok", "43.07", "131.54"),
    arrayListOf("Saratov", "51.32", "46.00"),
    arrayListOf("Volgograd", "48.70", "44.51"),
    arrayListOf("Bonn", "50.73", "7.09"),
    arrayListOf("Magnitogorsk", "53.23", "59.02"),
    arrayListOf("Frankfurt-am-Main", "50.06", "8.30"),
    arrayListOf("London", "51.30", "00.07"),
    arrayListOf("Stuttgart", "48.46", "9.10"),
    arrayListOf("Tobolsk", "58.20", "68.25"),
    arrayListOf("Salehard", "66.32", "66.38"),
    arrayListOf("Ioshkar-Ola", "56.37", "47.53"),
    arrayListOf("Tolliatti", "53.53", "49.34"),
    arrayListOf("Pnompen", "55.41", "12.35"),
    arrayListOf("Orel", "52.58", "36.05"),
    arrayListOf("Zheleznegorsk", "56.25", "93.53"),
    arrayListOf("Trehgornyy", "54.81", "58.45"),
    arrayListOf("Belgrade", "44.49", "20.28")
)


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            showInfo()
        }
    }
}

fun APICall(
    stateLocation: MutableState<String>,
    stateWeatherTemperature: MutableState<String>,
    stateWeatherFeelsLike: MutableState<String>,
    stateWeatherMaxTemperature: MutableState<String>,
    stateWeatherMinTemperature: MutableState<String>,
    stateWeatherType: MutableState<String>,
    lat : String,
    lon : String
) {
    executor.execute {
        val APIkey = "c2ce0be54eeb289483acdd10c56d282b"

        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$APIkey&units=metric"
        val recievedInfo = URL(url).readText()
        val jsonInfo = JSONObject(recievedInfo)

        Log.d("Recieved info", recievedInfo)

        var weatherCondition = jsonInfo.getJSONObject("main")
        location = jsonInfo.getString("name")
        weatherTemperature = weatherCondition.getString("temp")
        weatherFeelsLike = weatherCondition.getString("feels_like")
        weatherMaxTemperature = weatherCondition.getString("temp_max")
        weatherMinTemperature = weatherCondition.getString("temp_min")

        var weatherConditionArray = jsonInfo.getJSONArray("weather")
        weatherCondition = weatherConditionArray.getJSONObject(0)
        weatherType = weatherCondition.getString("main")

        weatherCondition = jsonInfo.getJSONObject("wind")
        weatherWindSpeed = weatherCondition.getInt("speed")

        weatherCondition = jsonInfo.getJSONObject("clouds")
        weatherCloudness = weatherCondition.getInt("all")

        stateLocation.value = location
        stateWeatherTemperature.value = weatherTemperature
        stateWeatherFeelsLike.value = weatherFeelsLike
        stateWeatherMaxTemperature.value = weatherMaxTemperature
        stateWeatherMinTemperature.value = weatherMinTemperature
        stateWeatherType.value = weatherType
    }
}

@Preview(showSystemUi = true)
@Composable
fun showInfo() {
    val stateLocation = remember {
        mutableStateOf("Unknown")
    }
    val stateWeatherTemperature = remember {
        mutableStateOf("Unknown")
    }
    val stateWeatherFeelsLike = remember {
        mutableStateOf("Unknown")
    }
    val stateWeatherMaxTemperature = remember {
        mutableStateOf("Unknown")
    }
    val stateWeatherMinTemperature = remember {
        mutableStateOf("Unknown")
    }
    val stateWeatherType = remember {
        mutableStateOf("Unknown")
    }
    var icTypeId = remember{
        mutableStateOf(0)
    }

    when(weatherType){
        "Clear" -> icTypeId.value = R.drawable.ic_sunny
        "Clouds",
        "Haze",
        "Dust",
        "Fog",
        "Sand",
        "Ash",
        "Squall",
        "Tornado"-> icTypeId.value = R.drawable.ic_cloudy
        "Snow" -> icTypeId.value = R.drawable.ic_snowy
        "Rain",
        "Drizzle"-> icTypeId.value = R.drawable.is_rain
        "Thunderstorm" -> icTypeId.value = R.drawable.ic_storm
        else -> {
            icTypeId.value = R.drawable.ic_sunny
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    2.dp,
                    Color.LightGray,
                    RoundedCornerShape(15.dp)
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Right now in",
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "" + stateLocation.value,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(modifier = Modifier
            .border(2.dp, Color.LightGray, RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.2f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
            {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            ) {
                if(icTypeId.value != 0) {
                    Image(
                        painter = painterResource(id = icTypeId.value),
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .clip(RoundedCornerShape(90.dp)),
                        contentDescription = "ic_clear_night",
                        alignment = Alignment.Center
                    )
                }
                Text(
                    text = "" + stateWeatherType.value,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stateWeatherTemperature.value + "°C",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Feels like " + stateWeatherFeelsLike.value + "°C",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Daily max/min: \n" + stateWeatherMaxTemperature.value + "°C/" + stateWeatherMinTemperature.value + "°C",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxHeight(),
                   horizontalAlignment = Alignment.CenterHorizontally) {
            for(i in 0 until citiesList.size){
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(5.dp)
                            .border(2.dp, Color.LightGray, RoundedCornerShape(15.dp))
                            .clickable {
                                APICall(
                                    stateLocation,
                                    stateWeatherTemperature,
                                    stateWeatherFeelsLike,
                                    stateWeatherMaxTemperature,
                                    stateWeatherMinTemperature,
                                    stateWeatherType,
                                    citiesList
                                        .get(i)
                                        .get(1),
                                    citiesList
                                        .get(i)
                                        .get(2)
                                )
                            }) {
                        Text(
                            text = citiesList.get(i).get(0),
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}