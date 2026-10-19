package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.UnfoldLess
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.PlanComptableRepository
import com.example.model.ElementLevel
import com.example.ui.components.AccountRow
import com.example.ui.components.BadgeSize
import com.example.ui.components.ClassBadge
import com.example.ui.theme.LedgerClassColors
import com.example.viewmodel.MainViewModel

@Composable
fun ClassExplorerScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val selectedClass by viewModel.selectedClassNumber.collectAsState()
    val visibleElements by viewModel.flattenedVisibleTree.collectAsState()
    val expandedUids by viewModel.expandedUids.collectAsState()
    val favoriteUids by viewModel.favoriteUids.collectAsState()

    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val classColor = LedgerClassColors.forClass(selectedClass)

    val classNumbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
    val currentIndex = classNumbers.indexOf(selectedClass)

    val chipListState = rememberLazyListState()
    val treeListState = rememberLazyListState()

    LaunchedEffect(selectedClass) {
        val idx = classNumbers.indexOf(selectedClass)
        if (idx >= 0) {
            chipListState.animateScrollToItem(idx)
        }
        treeListState.scrollToItem(0)
    }

    val classRoot = remember(selectedClass) {
        PlanComptableRepository.getElementsByClass(selectedClass).firstOrNull { it.level == ElementLevel.CLASSE }
    }

    val totalClassElements = remember(selectedClass) {
        PlanComptableRepository.getElementsByClass(selectedClass).size
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("class_explorer_screen")
    ) {
        // Sticky Header with Search Trigger & Class Chips
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(role = Role.Button) { viewModel.openSearch() },
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "بحث",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ابحث في الحسابات...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Row {
                        IconButton(
                            onClick = {
                                val prevIdx = if (currentIndex > 0) currentIndex - 1 else classNumbers.size - 1
                                viewModel.selectClass(classNumbers[prevIdx])
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "السابق", tint = classColor)
                        }
                        IconButton(
                            onClick = {
                                val nextIdx = (currentIndex + 1) % classNumbers.size
                                viewModel.selectClass(classNumbers[nextIdx])
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "التالي", tint = classColor)
                        }
                    }
                }

                LazyRow(
                    state = chipListState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(classNumbers) { cNum ->
                        val isSelected = cNum == selectedClass
                        val color = LedgerClassColors.forClass(cNum)

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) color else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (isSelected) {
                                if (isDark) Color(0xFF0F172A) else Color.White
                            } else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .clickable { viewModel.selectClass(cNum) }
                                .testTag("select_class_chip_$cNum")
                        ) {
                            Text(
                                text = "صنف $cNum",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline)

        // Tree Content
        LazyColumn(
            state = treeListState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = classColor.copy(alpha = if (isDark) 0.18f else 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, classColor.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ClassBadge(classNumber = selectedClass, size = BadgeSize.MEDIUM)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = classRoot?.officialFrenchTitle ?: "CLASSE $selectedClass",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = classColor
                                )
                                Text(
                                    text = classRoot?.arabicTitle ?: "",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = classRoot?.statementType?.labelAr ?: "",
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }

                            Text(
                                text = "$totalClassElements بنداً مسجلاً",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Controls: Expand all / Collapse all
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 2.dp),
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
                            Text("توسيع الكل", style = MaterialTheme.typography.labelSmall)
                        }

                        OutlinedButton(
                            onClick = { viewModel.collapseAllInClass() },
                            modifier = Modifier.testTag("collapse_all_btn")
                        ) {
                            Icon(imageVector = Icons.Default.UnfoldLess, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("طي الكل", style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    Text(
                        text = "المعروض: ${visibleElements.size}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Tree rows with UID keys
            items(visibleElements, key = { it.uid }) { element ->
                val children = PlanComptableRepository.getChildren(element.uid)
                val hasChildren = children.isNotEmpty()
                val isExpanded = expandedUids.contains(element.uid)
                val isFav = favoriteUids.contains(element.uid)

                AccountRow(
                    element = element,
                    isFavorite = isFav,
                    hasChildren = hasChildren,
                    isExpanded = isExpanded,
                    onToggleExpand = if (hasChildren) { { viewModel.toggleExpanded(element.uid) } } else null,
                    onToggleFavorite = { viewModel.toggleFavorite(element.uid) },
                    onClick = { viewModel.openAccountDetails(element) },
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
        }
    }
}
