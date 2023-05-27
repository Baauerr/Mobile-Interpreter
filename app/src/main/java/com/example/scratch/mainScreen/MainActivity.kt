package com.example.scratch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.scratch.mainScreen.MainScreen

class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Image(painter = painterResource(id = R.drawable.mobilebackground), contentDescription = null, modifier = Modifier.fillMaxSize())
            MainScreen()
        }
    }
}
