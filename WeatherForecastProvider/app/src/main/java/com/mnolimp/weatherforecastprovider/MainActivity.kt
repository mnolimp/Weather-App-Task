package com.mnolimp.weatherforecastprovider

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors

private val executor = Executors.newSingleThreadExecutor()
private var location: String = "Unknown"
private var weatherTemperature : String = "Zero"
private var weatherFeelsLike : String = "Zero"
private var weatherMaxTemperature : String = "Zero"
private var weatherMinTemperature : String = "Zero"
private var weatherType : String = "Unknown"
private var weatherCloudness : Int = 0
private var weatherWindSpeed : Int = 0

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            showInfo()
        }
    }
}

fun APICall(stateLocation: MutableState<String>,
            stateWeatherTemperature: MutableState<String>,
            stateWeatherFeelsLike: MutableState<String>,
            stateWeatherMaxTemperature: MutableState<String>,
            stateWeatherMinTemperature: MutableState<String>,
            stateWeatherType: MutableState<String>
) {
    executor.execute {
        val APIkey = "c2ce0be54eeb289483acdd10c56d282b"
        val lat = "44.34"
        val lon = "10.99"

        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$APIkey&units=metric"
        val recievedInfo = URL(url).readText()

        Log.d("Recieved info", recievedInfo)

        val jsonInfo = JSONObject(recievedInfo)

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

        Log.d("City", location)
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

    APICall(
        stateLocation,
        stateWeatherTemperature,
        stateWeatherFeelsLike,
        stateWeatherMaxTemperature,
        stateWeatherMinTemperature,
        stateWeatherType
    )

    when(weatherType){
        "Clear" -> icTypeId.value = R.drawable.ic_sunny
        "Clouds" -> icTypeId.value = R.drawable.ic_cloudy
        "Snow" -> icTypeId.value = R.drawable.ic_snowy
        "Rain" -> icTypeId.value = R.drawable.is_rain
        "Drizzle" -> icTypeId.value = R.drawable.is_rain
        "Thunderstorm" -> icTypeId.value = R.drawable.ic_storm
        else -> {
            icTypeId.value = R.drawable.ic_sunny
        }
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                Text(
                    text = "Right now",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                )
                Text(
                    text = "" + stateWeatherType.value,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "City:" + stateLocation.value,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Right
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .background(color = Color.Green)
            ) {
                if(icTypeId.value != 0) {
                    Image(
                        painter = painterResource(id = icTypeId.value),
                        modifier = Modifier.fillMaxWidth(0.4f),
                        contentDescription = "ic_clear_night"
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Blue)
            ) {
                Text(
                    text = stateWeatherTemperature.value + "°C",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Left
                )
                Text(
                    text = "Feels like " + stateWeatherFeelsLike.value + "°C",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Left
                )
                Text(
                    text = "Daily max/min: \n" + stateWeatherMaxTemperature.value + "°C/" + stateWeatherMinTemperature.value + "°C",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Left
                )
            }
        }
    }

    /*Column {
        Column {
            Text(text = "Right now", modifier = Modifier.fillMaxWidth(1f), fontSize = 20.sp)
        }
        Column {
            Text(
                text = "City:" + stateLocation.value,
                modifier = Modifier.fillMaxWidth(1f),
                fontSize = 50.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Current temp: " + stateWeatherTemperature.value,
                modifier = Modifier.fillMaxWidth(1f),
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Feels like: " + stateWeatherFeelsLike.value,
                modifier = Modifier.fillMaxWidth(1f),
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "" + stateWeatherMaxTemperature.value,
                modifier = Modifier.fillMaxWidth(1f),
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "" + stateWeatherMinTemperature.value,
                modifier = Modifier.fillMaxWidth(1f),
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(onClick = {
            APICall(
                stateLocation,
                stateWeatherTemperature,
                stateWeatherFeelsLike,
                stateWeatherMaxTemperature,
                stateWeatherMinTemperature
            )
        },
            Modifier
                .padding(5.dp)
                .fillMaxWidth(1f)) {
            Text(text = "Refresh")
        }
    }*/
}