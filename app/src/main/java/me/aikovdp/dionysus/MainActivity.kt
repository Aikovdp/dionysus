package me.aikovdp.dionysus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import me.aikovdp.dionysus.ui.MainNavigation
import me.aikovdp.dionysus.ui.theme.DionysusTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DionysusTheme {
                MainNavigation()
            }
        }
    }
}
