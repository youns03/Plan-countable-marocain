package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.UnfoldLess
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PlanComptableRepository
import com.example.model.AccountingElement
import com.example.model.ElementLevel
import com.example.ui.components.AccountCard
import com.example.ui.theme.ClassColors
import com.example.ui.theme.CreditColor
import com.example.ui.theme.DebitColor
import com.example.viewmodel.MainViewModel

@Composable
fun ClassExplorerScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val selectedClass by viewModel.selectedClassNumber.collectAsState()
    val expandedCodes by viewModel.expandedCodes.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val languageMode by viewModel.languageMode.collectAsState()
    val largeFont by viewModel.largeFont.collectAsState()

    val classElements = PlanComptableRepository.getElementsByClass(selectedClass)
    val classColor = ClassColors.forClass(selectedClass)
    val classRoot = classElements.firstOrNull { it.level == ElementLevel.CLASSE }

    val classNumbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
    val currentIndex = classNumbers.indexOf(selectedClass)

    // Build visible hierarchy based on expanded state:
    // Elements are shown if they are CLASSE or RUBRIQUE, or if their parent is expanded
    fun isElementVisible(elem: AccountingElement): Boolean {
        if (elem.level == ElementLevel.CLASSE || elem.level == ElementLevel.RUBRIQUE) return true
        val parent = elem.parentCode ?: return true
        if (!expandedCodes.contains(parent)) return false
        val parentElem = PlanComptableRepository.getElementByCode(parent) ?: return true
        return isElementVisible(parentElem)
    }

    val visibleElements = classElements.filter { isElementVisible(it) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("class_explorer_screen")
    ) {
        // Class Selector Chips Row
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(classNumbers) { cNum ->
                    val isSelected = cNum == selectedClass
                    val chipColor = ClassColors.forClass(cNum)

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) chipColor else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clickable { viewModel.selectClass(cNum) }
                            .testTag("select_class_chip_$cNum")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "صنف $cNum",
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            // Class Hero Header
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = classColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White.copy(alpha = 0.25f),
                                    contentColor = Color.White
                                ) {
                                    Text(
                                        text = "Classe $selectedClass",
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = classRoot?.statementType?.labelAr ?: "",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }

                            // Account count badge
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.White.copy(alpha = 0.2f),
                                contentColor = Color.White
                            ) {
                                Text(
                                    text = "${classElements.size} عنصر",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = classRoot?.officialFrenchTitle ?: "CLASSE $selectedClass",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = classRoot?.arabicTitle ?: "",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.95f),
                            lineHeight = 21.sp
                        )

                        if (!classRoot?.shortExplanationAr.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = classRoot.shortExplanationAr,
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.85f),
                                lineHeight = 17.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Normal Balance Pill
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            contentColor = Color.White
                        ) {
                            Text(
                                text = "الرصيد الطبيعي: ${classRoot?.normalBalance?.labelAr ?: ""}",
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Controls: Expand/Collapse All + Previous/Next Class
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { viewModel.expandAllInClass() },
                            modifier = Modifier.testTag("expand_all_btn")
                        ) {
                            Icon(imageVector = Icons.Default.UnfoldMore, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("توسيع الكل", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = { viewModel.collapseAllInClass() },
                            modifier = Modifier.testTag("collapse_all_btn")
                        ) {
                            Icon(imageVector = Icons.Default.UnfoldLess, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("طي الكل", fontSize = 12.sp)
                        }
                    }

                    // Prev / Next Class
                    Row {
                        IconButton(
                            onClick = {
                                val prevIndex = if (currentIndex > 0) currentIndex - 1 else classNumbers.size - 1
                                viewModel.selectClass(classNumbers[prevIndex])
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "الصنف السابق", tint = classColor)
                        }

                        IconButton(
                            onClick = {
                                val nextIndex = (currentIndex + 1) % classNumbers.size
                                viewModel.selectClass(classNumbers[nextIndex])
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "الصنف التالي", tint = classColor)
                        }
                    }
                }
            }

            // Tree Elements
            items(visibleElements, key = { it.code }) { element ->
                val children = PlanComptableRepository.getChildren(element.code)
                val hasChildren = children.isNotEmpty()
                val isExpanded = expandedCodes.contains(element.code)
                val isFav = favorites.contains(element.code)

                // Indentation based on level
                val startPadding = when (element.level) {
                    ElementLevel.CLASSE -> 0.dp
                    ElementLevel.RUBRIQUE -> 0.dp
                    ElementLevel.POSTE -> 14.dp
                    ElementLevel.COMPTE -> 26.dp
                    ElementLevel.SOUS_COMPTE -> 38.dp
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = startPadding + 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                ) {
                    AccountCard(
                        element = element,
                        languageMode = languageMode,
                        isFavorite = isFav,
                        hasChildren = hasChildren,
                        isExpanded = isExpanded,
                        largeFont = largeFont,
                        onToggleExpand = if (hasChildren) { { viewModel.toggleExpanded(element.code) } } else null,
                        onToggleFavorite = { viewModel.toggleFavorite(element.code) },
                        onClick = { viewModel.openAccountDetails(element) }
                    )
                }
            }
        }
    }
}
