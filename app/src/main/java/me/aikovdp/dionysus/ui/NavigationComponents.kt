package me.aikovdp.dionysus.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun DionysusNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (DionysusTopLevelDestination) -> Unit
) {
    NavigationBar {
        TOP_LEVEL_DESTINATIONS.forEach { item ->
            val selected = selectedDestination == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { navigateToTopLevelDestination(item) },
                icon = {
                    Icon(
                        if (selected) item.selectedIcon else item.unselectedIcon,
                        stringResource(item.iconTextId)
                    )
                },
                label = { Text(stringResource(item.iconTextId)) }
            )
        }
    }
}
