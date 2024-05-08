package com.example.sortingvizualizer.MergeSortActivity.presentation


import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sortingvizualizer.MergeSortActivity.domain.model.use_case.MergeSortUseCase
import com.example.sortingvizualizer.MergeSortActivity.presentation.state.SortInfoUiItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.UUID

class SortViewModel(
    private val mergeSortUseCase: MergeSortUseCase = MergeSortUseCase(),
    var listToSort: MutableList<Int> = mutableListOf()
) :
    ViewModel() {


    val sortInfoUiItemList = mutableStateListOf<SortInfoUiItem>()
    private var sortingPaused = false
    private var job: Job? = null
    fun startSorting() {
        sortInfoUiItemList.clear()

        Log.d("list", "$listToSort")
        viewModelScope.launch {
            mergeSortUseCase(listToSort, 0)
        }
        subscribeToSortChanges()

    }




    private fun subscribeToSortChanges() {
        job?.cancel()
        job = viewModelScope.launch {
            mergeSortUseCase.sortFlow.collect { sortInfo ->
                val depthAlreadyExistsListIndex = sortInfoUiItemList.indexOfFirst {
                    it.depth == sortInfo.depth && it.sortState == sortInfo.sortState
                }

                if (depthAlreadyExistsListIndex == -1) {
                    sortInfoUiItemList.add(
                        SortInfoUiItem(
                            id = UUID.randomUUID().toString(),
                            depth = sortInfo.depth,
                            sortParts = listOf(sortInfo.sortParts),
                            sortState = sortInfo.sortState,
                            color = Color(
                                (0..200).random(),
                                (0..200).random(),
                                (0..200).random(),
                                255
                            )
                        )
                    )
                } else {
                    val currentPartList =
                        sortInfoUiItemList[depthAlreadyExistsListIndex].sortParts.toMutableList()
                    currentPartList.add(sortInfo.sortParts)
                    sortInfoUiItemList[depthAlreadyExistsListIndex] =
                        sortInfoUiItemList[depthAlreadyExistsListIndex].copy(
                            sortParts = currentPartList
                        )
                }

                sortInfoUiItemList.sortedWith(
                    compareBy(
                        {
                            it.sortState
                        }, {
                            it.depth
                        }
                    )
                )

            }
        }
    }
}
