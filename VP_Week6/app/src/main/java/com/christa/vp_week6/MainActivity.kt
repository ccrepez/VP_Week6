package com.christa.vp_week6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.christa.vp_week6.navigation.CampusNavHost
import com.christa.vp_week6.ui.theme.VP_Week6Theme
import com.christa.vp_week6.viewmodel.CampusViewModel

class MainActivity : ComponentActivity() {
    private val campusViewModel: CampusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VP_Week6Theme {
                CampusNavHost(viewModel = campusViewModel)
            }
        }
    }
}