package me.aikovdp.dionysus.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigation.suite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteScope
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
fun navigationSuiteItems(
    selectedDestination: String,
    navigateToTopLevelDestination: (DionysusTopLevelDestination) -> Unit
): NavigationSuiteScope.() -> Unit = {
    TOP_LEVEL_DESTINATIONS.forEach { item ->
        val selected = selectedDestination == item.route
        item(
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
