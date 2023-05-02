package com.example.satelliteslocationdetermine.ui.viewModel

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satelliteslocationdetermine.data.UIState
import com.example.satelliteslocationdetermine.data.repository.SatelliteRepository
import com.example.satelliteslocationdetermine.data.response.Position
import com.example.satelliteslocationdetermine.data.response.Positions
import com.example.satelliteslocationdetermine.data.response.SatelliteDetailResponseModel
import com.example.satelliteslocationdetermine.data.response.SatellitePositionResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val assetManager: AssetManager,
    private val repository: SatelliteRepository
) : ViewModel() {

    private val _satelliteDetailUIState =
        MutableLiveData<UIState<SatelliteDetailResponseModel>>(UIState())
    var satelliteDetailUIState: LiveData<UIState<SatelliteDetailResponseModel>> =
        _satelliteDetailUIState

    private val _positionUIState = MutableLiveData<UIState<Position>>(UIState())
    var positionUIState: LiveData<UIState<Position>> = _positionUIState

    fun getSatelliteDetail(id: Long) {
        _satelliteDetailUIState.value = UIState(loading = true)
        viewModelScope.launch {
            delay(1000)
            val getDetail = repository.getSatelliteDetail(id)
            if (getDetail != null) {
                _satelliteDetailUIState.value =
                    UIState(loading = false, data = repository.getSatelliteDetail(id))
            } else {
                val result = withContext(Dispatchers.IO) {
                    val json = assetManager.open("satellite_detail.json").bufferedReader().use { it.readText() }
                    val list = Json.decodeFromString<MutableList<SatelliteDetailResponseModel>>(json)
                    list
                }
                try {
                    val item = result.find { it.id == id }
                    item?.let { repository.addSatellite(it) }
                    _satelliteDetailUIState.value =
                        UIState(loading = false, data = item)

                } catch (e: Exception) {
                    _satelliteDetailUIState.value = UIState(loading = false, error = e)
                }
            }
        }
    }

    fun getPosition(id: Long) {
        _positionUIState.value = UIState(loading = true)
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                val json = assetManager.open("position.json").bufferedReader().use { it.readText() }
                val list = Json.decodeFromString<SatellitePositionResponseModel>(json)
                list
            }
            try {
                val selectedPositions = result.list.find { it.id == id.toString() }
                selectedPositions?.positions?.forEach { position ->
                    _positionUIState.postValue(UIState(loading = true))
                    delay(1000)
                    _positionUIState.postValue(
                        UIState(
                            loading = false,
                            data = position
                        )
                    )
                    delay(3000)

                }
            } catch (e: Exception) {
                _positionUIState.value = UIState(loading = false, error = e)
            }
        }
    }
}