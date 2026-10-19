package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ui.components.AccountDetailSheet
import com.example.ui.screens.ClassExplorerScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.MyAccountScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.theme.LocalLanguageMode
import com.example.ui.theme.PcmTokens
import com.example.ui.theme.PlanComptableTheme
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
            val languageMode by viewModel.languageMode.collectAsState()
            val largeFont by viewModel.largeFont.collectAsState()

            val currentDensity = LocalDensity.current
            val adjustedDensity = if (largeFont) {
                Density(
                    density = currentDensity.density,
                    fontScale = currentDensity.fontScale * 1.15f
                )
            } else {
                currentDensity
            }

            // Root Providers: RTL layout, Dynamic Font Scale, and Language Mode
            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Rtl,
                LocalDensity provides adjustedDensity,
                LocalLanguageMode provides languageMode
            ) {
                PlanComptableTheme(
                    darkTheme = useDarkTheme
                ) {
                    MainApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainApp(viewModel: MainViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val isSearchOpen by viewModel.isSearchOpen.collectAsState()
    val selectedAccount by viewModel.selectedAccount.collectAsState()
    val favoriteUids by viewModel.favoriteUids.collectAsState()

    // Smart Back Handling: Sheet -> Search -> Home Tab
    BackHandler(enabled = selectedAccount != null || isSearchOpen || currentTab != AppTab.HOME) {
        when {
            selectedAccount != null -> viewModel.closeAccountDetails()
            isSearchOpen -> viewModel.closeSearch()
            currentTab != AppTab.HOME -> viewModel.selectTab(AppTab.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (!isSearchOpen) {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("bottom_nav_bar"),
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 4.dp
                ) {
                    val navItems = listOf(
                        Triple(AppTab.HOME, "الرئيسية", Icons.Default.Home),
                        Triple(AppTab.TREE, "الشجرة", Icons.Default.AccountTree),
                        Triple(AppTab.LEARN, "التعلّم", Icons.Default.School),
                        Triple(AppTab.ACCOUNT, "حسابي", Icons.Default.ManageAccounts)
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
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PcmTokens.PrimaryLight,
                                selectedTextColor = PcmTokens.PrimaryLight,
                                indicatorColor = PcmTokens.PrimaryLight.copy(alpha = 0.12f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier.testTag("nav_item_${tab.name.lowercase()}")
                        )
                    }
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
                AppTab.TREE -> ClassExplorerScreen(viewModel = viewModel)
                AppTab.LEARN -> LearnScreen(viewModel = viewModel)
                AppTab.ACCOUNT -> MyAccountScreen(viewModel = viewModel)
            }

            // Fullscreen Search Transition Overlay
            AnimatedVisibility(
                visible = isSearchOpen,
                enter = slideInVertically(initialOffsetY = { it / 4 }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it / 4 }) + fadeOut()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchScreen(
                        viewModel = viewModel,
                        onClose = { viewModel.closeSearch() }
                    )
                }
            }
        }
    }

    // Account Detail Bottom Sheet Modal
    selectedAccount?.let { element ->
        val isFav = favoriteUids.contains(element.uid)
        AccountDetailSheet(
            element = element,
            isFavorite = isFav,
            onToggleFavorite = { viewModel.toggleFavorite(element.uid) },
            onSelectAncestor = { ancestor -> viewModel.openAccountDetails(ancestor) },
            onSelectEntry = { entryId ->
                viewModel.closeAccountDetails()
                viewModel.selectJournalEntry(entryId)
            },
            onDismiss = { viewModel.closeAccountDetails() }
        )
    }
}
