package com.example.resumeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ResumeViewModel : ViewModel() {
    private val _resumeData = mutableStateOf<ResumeData?>(null)
    val resumeData : State<ResumeData?> = _resumeData

    private val _location = mutableStateOf<LocationData?>(null)
    val location : State<LocationData?> = _location

    private val _isLoading = mutableStateOf(false)
    val isLoading : State<Boolean> = _isLoading

    var fontSize = mutableStateOf(16f)
    var textColor = mutableStateOf(Color.Black)
    var backgroundColor = mutableStateOf(Color.White)

    fun fetchResumeData(name : String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val data = RetrofitInstance.api.getResume(name)
                _resumeData.value = data
            }catch (e : Exception){
                e.printStackTrace()
            }finally {
                _isLoading.value = false
            }
        }
    }


    fun updateLocation(newLocationData: LocationData){
        _location.value = newLocationData
    }
}