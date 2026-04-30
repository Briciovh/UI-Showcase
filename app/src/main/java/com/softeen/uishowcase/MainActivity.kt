package com.softeen.uishowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.softeen.uishowcase.navigation.AppNavigation
import com.softeen.uishowcase.ui.theme.UIShowcaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { UIShowcaseTheme { AppNavigation() } }
    }
}