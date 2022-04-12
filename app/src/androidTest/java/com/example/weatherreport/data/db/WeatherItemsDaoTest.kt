package com.example.weatherreport.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.weatherreport.data.api.items.WeatherItem
import com.example.weatherreport.data.api.items.WeatherItem.Condition
import com.example.weatherreport.data.api.items.WeatherItem.Current
import com.example.weatherreport.data.api.items.WeatherItem.Forecast
import com.example.weatherreport.data.api.items.WeatherItem.Location
import com.example.weatherreport.data.api.items.WeatherItem.Forecast.Forecastday.Hour
import com.example.weatherreport.data.api.items.WeatherItem.Forecast.Forecastday
import com.example.weatherreport.data.api.items.WeatherItem.Forecast.Forecastday.Day
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat

@RunWith(JUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class WeatherItemsDaoTest {
    private lateinit var database: WeatherDatabase
    private lateinit var dao: WeatherItemsDao
    private lateinit var weatherItemForTest: WeatherItem

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = database.weatherItemsDAO()

        val doubleValueForTest = 1.0
        val stringForTest = "Sample string or whatever"

        val conditionForTest = Condition(
            icon = stringForTest,
            text = stringForTest
        )

        weatherItemForTest = WeatherItem(
            current = Current(
                condition = conditionForTest,
                feelslike_c = doubleValueForTest,
                last_updated = stringForTest,
                temp_c = doubleValueForTest
            ),
            forecast = Forecast(
                forecastday = List(10) {
                    Forecastday(
                        date = stringForTest,
                        day = Day(
                            condition = conditionForTest,
                            avgtemp_c = doubleValueForTest,
                            maxtemp_c = doubleValueForTest,
                            mDoubleemp_c = doubleValueForTest
                        ),
                        hour = List(24) {
                            Hour(
                                condition = conditionForTest,
                                feelslike_c = doubleValueForTest,
                                temp_c = doubleValueForTest,
                                time = stringForTest
                            )
                        }
                    )
                }
            ),
            location = Location(
                country = stringForTest,
                name = stringForTest
            )
        )
    }


    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertSingleWeatherItemIntoDatabaseAndCheckThatItExistsThere() = runTest {
        dao.cacheWeatherItem(weatherItemForTest)

        val listOfItems = dao.getCityByName(weatherItemForTest.location.name).first()

        assertThat(listOfItems).hasSize(1)
        assertThat(listOfItems).contains(weatherItemForTest)
    }

    @Test
    fun getNullWhenThereIsNoItemWithSuchName() = runTest {
        val itemsList = dao.getCityByName("SampleName").first()

        assertThat(itemsList).isEmpty()
    }

    @Test
    fun insertOneItemTwoTimesButCacheOnlyOneOfThem() = runTest {
        dao.cacheWeatherItem(weatherItemForTest)
        dao.cacheWeatherItem(weatherItemForTest)

        val cachedItemsList = dao.getAllCachedItems().first()

        assertThat(cachedItemsList.size).isEqualTo(1)
        assertThat(cachedItemsList).contains(weatherItemForTest)
    }

    @Test
    fun insertWeatherItemThenDeleteItSoCachedItemsListWillBeEmpty() = runTest {
        dao.cacheWeatherItem(weatherItemForTest)
        dao.deleteCityByName(weatherItemForTest.location.name)

        val cachedList = dao.getAllCachedItems().first()

        assertThat(cachedList).isEmpty()
    }

    @Test
    fun insertItemAndThenDeleteWithLowercaseName() = runTest {
        dao.cacheWeatherItem(weatherItemForTest)
        dao.deleteCityByName(weatherItemForTest.location.name.lowercase())

        val cachedList = dao.getAllCachedItems().first()

        assertThat(cachedList).isEmpty()
    }

    @Test
    fun insertItemAndThenDeleteWithUppercaseName() = runTest {
        dao.cacheWeatherItem(weatherItemForTest)
        dao.deleteCityByName(weatherItemForTest.location.name.uppercase())

        val cachedList = dao.getAllCachedItems().first()

        assertThat(cachedList).isEmpty()
    }
}