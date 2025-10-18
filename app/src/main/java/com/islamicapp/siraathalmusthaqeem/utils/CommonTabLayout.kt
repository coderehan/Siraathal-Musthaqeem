package com.islamicapp.siraathalmusthaqeem.utils

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CommonTabLayout(
    modifier: Modifier = Modifier,
    tabTitles: List<String>,
    backgroundColor: Color = Color.White,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable (pageIndex: Int, title: String) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = backgroundColor,
            contentColor = selectedColor,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                AnimatedTabIndicator(pagerState.currentPage, tabPositions, indicatorColor)
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                val selected = pagerState.currentPage == index
                Tab(
                    selected = selected,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    },
                    text = {
                        Text(
                            text = title,
                            color = if (selected) selectedColor else unselectedColor,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            content(page, tabTitles[page])
        }
    }
}

@Composable
private fun AnimatedTabIndicator(
    selectedTabIndex: Int,
    tabPositions: List<TabPosition>,
    indicatorColor: Color
) {
    if (tabPositions.isEmpty()) return

    val currentTab = tabPositions[selectedTabIndex]
    val indicatorLeft by animateDpAsState(targetValue = currentTab.left, label = "indicatorLeft")
    val indicatorWidth by animateDpAsState(targetValue = currentTab.width, label = "indicatorWidth")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorWidth)
            .height(3.dp)
            .background(indicatorColor)
    )
}
