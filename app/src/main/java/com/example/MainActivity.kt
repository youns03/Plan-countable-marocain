package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AccountDetailModal
import com.example.ui.components.FavoritesDialog
import com.example.ui.screens.ClassExplorerScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsAndAuditScreen
import com.example.ui.theme.PlanComptableTheme
import com.example.ui.theme.PrimaryBlue
import com.example.viewmodel.AppTab
import com.example.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkThemePref by viewModel.isDarkTheme.collectAsState()
            val useDarkTheme = isDarkThemePref ?: isSystemInDarkTheme()

            PlanComptableTheme(darkTheme = useDarkTheme) {
                MainApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainApp(viewModel: MainViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val selectedAccount by viewModel.selectedAccount.collectAsState()
    val showFavoritesDialog by viewModel.showFavoritesDialog.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    // Handle back button gracefully
    BackHandler(enabled = selectedAccount != null || showFavoritesDialog || currentTab != AppTab.HOME) {
        when {
            selectedAccount != null -> viewModel.closeAccountDetails()
            showFavoritesDialog -> viewModel.setShowFavoritesDialog(false)
            currentTab != AppTab.HOME -> viewModel.selectTab(AppTab.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("bottom_nav_bar"),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                val navItems = listOf(
                    Triple(AppTab.HOME, "الرئيسية", Icons.Default.Home),
                    Triple(AppTab.CLASSES, "الأقسام", Icons.Default.AccountTree),
                    Triple(AppTab.SEARCH, "البحث", Icons.Default.Search),
                    Triple(AppTab.LEARN, "التعلّم", Icons.Default.School),
                    Triple(AppTab.SETTINGS, "الإعدادات", Icons.Default.Settings)
                )

                navItems.forEach { (tab, label, icon) ->
                    val isSelected = currentTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.selectTab(tab) },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryBlue,
                            selectedTextColor = PrimaryBlue,
                            indicatorColor = PrimaryBlue.copy(alpha = 0.15f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.testTag("nav_item_${tab.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppTab.HOME -> HomeScreen(viewModel = viewModel)
                AppTab.CLASSES -> ClassExplorerScreen(viewModel = viewModel)
                AppTab.SEARCH -> SearchScreen(viewModel = viewModel)
                AppTab.LEARN -> LearnScreen(viewModel = viewModel)
                AppTab.SETTINGS -> SettingsAndAuditScreen(viewModel = viewModel)
            }
        }
    }

    // Account Detail Bottom Sheet Modal
    selectedAccount?.let { element ->
        val isFav = favorites.contains(element.code)
        AccountDetailModal(
            element = element,
            isFavorite = isFav,
            onToggleFavorite = { viewModel.toggleFavorite(element.code) },
            onSelectAncestor = { ancestor -> viewModel.openAccountDetails(ancestor) },
            onDismiss = { viewModel.closeAccountDetails() }
        )
    }

    // Favorites Bottom Sheet Dialog
    if (showFavoritesDialog) {
        FavoritesDialog(
            viewModel = viewModel,
            favorites = favorites,
            onDismiss = { viewModel.setShowFavoritesDialog(false) }
        )
    }
}
