package com.example.sortingvizualizer.MergeSortActivity.presentation.state


import androidx.compose.ui.graphics.Color
import com.example.sortingvizualizer.MergeSortActivity.domain.model.SortState

data class SortInfoUiItem(
    val id: String,
    val depth: Int,
    val sortParts: List<List<Int>>,
    val sortState: SortState,
    val color: Color
)
