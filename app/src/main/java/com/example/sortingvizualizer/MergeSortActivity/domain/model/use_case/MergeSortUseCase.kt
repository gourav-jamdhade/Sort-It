package com.example.sortingvizualizer.MergeSortActivity.domain.model.use_case

import com.example.sortingvizualizer.MergeSortActivity.domain.model.SortInfo
import com.example.sortingvizualizer.MergeSortActivity.domain.model.SortState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.UUID

class MergeSortUseCase {

    val sortFlow = MutableSharedFlow<SortInfo>()

    suspend operator fun invoke(list: List<Int>, depth: Int): List<Int> {
        delay(500)
        sortFlow.emit(
            SortInfo(
                id = UUID.randomUUID().toString(),
                depth = depth,
                sortParts = list,
                sortState = SortState.DIVIDED
            )
        )

        val listSize = list.size
        if (listSize <= 1) {
            return list
        }

        var leftList = list.slice(0 until (listSize + 1) / 2)
        var rightList = list.slice((listSize + 1) / 2 until listSize)

        leftList = this(leftList, depth + 1)
        rightList = this(rightList, depth + 1)

        return merge(leftList.toMutableList(), rightList.toMutableList(), depth)
    }

    private suspend fun merge(
        leftList: MutableList<Int>, rightList: MutableList<Int>, depth: Int
    ): List<Int> {
        val mergedList = mutableListOf<Int>()
        while(leftList.isNotEmpty() && rightList.isNotEmpty()){
            if(leftList.first()<= rightList.first()){
                mergedList.add(mergedList.size,leftList.removeFirst())
            }else{
                mergedList.add(mergedList.size,rightList.removeFirst())
            }
        }

        mergedList.addAll(leftList)
        mergedList.addAll(rightList)

        delay(500)
        sortFlow.emit(
            SortInfo(
                id = UUID.randomUUID().toString(),
                depth = depth,
                sortParts = mergedList,
                sortState = SortState.MERGED
            )
        )
        return mergedList
    }
}