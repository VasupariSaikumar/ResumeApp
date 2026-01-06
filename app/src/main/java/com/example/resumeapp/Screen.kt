package com.example.resumeapp

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeScreen(viewModel: ResumeViewModel  ) {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    val resumeData = viewModel.resumeData.value
    val location by viewModel.location
    val currentFontSize = viewModel.fontSize.value

    val requestLocationPermisson  = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {permissions->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION]==true &&
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION]==true){
            locationUtils.requestLocationUpdates(viewModel = viewModel)

        }else{
            val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                context as MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (rationaleRequired){
                Toast.makeText(context, " Location Required for this feature to Work " , Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "To Enable Location Permission Go to Settings and Enable Location " , Toast.LENGTH_LONG).show()
            }
        }

    }
    )

    LaunchedEffect(Unit) {
        requestLocationPermisson.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resume") },
                actions = {
                    Column(modifier = Modifier.padding(end = 16.dp)) {
                        Text(
                            text = "Lat:${location?.latitude} ",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = " Long:${location?.longitude}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text("Text Color")
            Row {
                val colors =listOf(Color.Black , Color.Red , Color.Green , Color.Blue)
                colors.forEach { color->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(4.dp)
                            .background(color , shape = CircleShape)
                            .clickable{viewModel.textColor.value = color}
                    )
                }
            }
            Text(text = "Font Size: ${viewModel.fontSize.value.toInt()}")
            Slider(
                value = currentFontSize,
                onValueChange = {
                    viewModel.fontSize.value = it
                },
                valueRange = 12f..40f,
            )
            Box(modifier = Modifier.padding(16.dp)) {
               val displayText = if(resumeData != null){
                   "${resumeData?.name}\n${resumeData?.summary}\nSkills : ${resumeData?.skills?.joinToString()}"
            }else{
                "Loading resume..."
               }
                Text(
                    text= displayText ,
                    fontSize = currentFontSize.sp,
                    color = viewModel.textColor.value
                )
            }
        }
    }
}
