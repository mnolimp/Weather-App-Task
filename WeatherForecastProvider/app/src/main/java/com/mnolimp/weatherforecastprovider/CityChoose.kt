package com.mnolimp.weatherforecastprovider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mnolimp.weatherforecastprovider.ui.theme.WeatherForecastProviderTheme

class CityChoose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

/*@Composable
fun showInfo2(){
    Column {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 0..citiesList.size - 1) {
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
}*/
