package com.example.satelliteslocationdetermine.ui.viewModel

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satelliteslocationdetermine.data.UIState
import com.example.satelliteslocationdetermine.data.response.SatelliteListResponseModel
import com.example.satelliteslocationdetermine.data.response.SatellitePositionResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class SatelliteViewModel @Inject constructor(
    private val assetManager: AssetManager
) : ViewModel() {

    private val _satelliteListUIState =
        MutableLiveData<UIState<MutableList<SatelliteListResponseModel>>>(UIState())
    var satelliteListUIState: LiveData<UIState<MutableList<SatelliteListResponseModel>>> =
        _satelliteListUIState

    fun getSatellites() {
        _satelliteListUIState.value = UIState(loading = true)
        viewModelScope.launch {
            delay(1000)
            val result = withContext(Dispatchers.IO) {
                val json =
                    assetManager.open("satellites.json").bufferedReader().use { it.readText() }
                val list = Json.decodeFromString<MutableList<SatelliteListResponseModel>>(json)
                list
            }
            try {
                /***
                 * //Basic JSON Parsing// *
            val list: MutableList<SatelliteListResponseModel> = mutableListOf()
                val json = assetManager.open("satellite_detail.json").bufferedReader().use { it.readText() }
                var jsonArr = JSONArray(json)
                for (i in 0 until jsonArr.length()) {
                    var jsonObj = jsonArr.getJSONObject(i)
                    list.add(
                        SatelliteListResponseModel(
                            id = jsonObj.getLong("id"),
                            name = jsonObj.getString("name"),
                            active = jsonObj.getBoolean("active")
                        )
                    )
                }
                ***/
                _satelliteListUIState.value = UIState(loading = false, data = result)
            } catch (e: IOException) {
                _satelliteListUIState.value = UIState(loading = false, error = e)
            }
        }
    }

}