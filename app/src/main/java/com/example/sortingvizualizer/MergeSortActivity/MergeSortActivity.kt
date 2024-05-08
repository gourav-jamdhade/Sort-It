package com.example.sortingvizualizer.MergeSortActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sortingvizualizer.MergeSortActivity.presentation.SortViewModel
import com.example.sortingvizualizer.MergeSortActivity.ui.theme.SortingVizualizerTheme
import com.example.sortingvizualizer.MergeSortActivity.ui.theme.gray

class MergeSortActivity : ComponentActivity() {

    private val sortViewModel by viewModels<SortViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arrayList = intent.getIntArrayExtra("randomArray")?.asList()
        val arrayMutableList = arrayList!!.toMutableList()
        sortViewModel.listToSort = arrayMutableList
        setContent {
            SortingVizualizerTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gray)
                        .padding(10.dp),

                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(
                            sortViewModel.sortInfoUiItemList
                        ) { index, sortInfoUiItem ->
                            val depthParts = sortInfoUiItem.sortParts
                            Log.d("index", "$index")
                            if (index == 0) {
                                Text(
                                    "Dividing",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )

                            }
                            if (index == arrayMutableList.size / 2) {
                                Text(
                                    "Merging",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )

                            }

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                for (part in depthParts) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        modifier = Modifier
                                            .padding(
                                                start = if (depthParts.indexOf(part) == null) 0.dp else 17.dp
                                            )
                                            .background(sortInfoUiItem.color)
                                            .padding(5.dp)
                                    ) {
                                        for (numberInfo in part) {
                                            if (part.indexOf(numberInfo) != part.size - 1) {
                                                Text(
                                                    "$numberInfo |",
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    fontSize = 19.sp
                                                )
                                            } else {
                                                Text(
                                                    "$numberInfo",
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    fontSize = 19.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(gray)
                            .padding(15.dp)
                            .align(Alignment.BottomCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            "${arrayMutableList}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Button(onClick = {
                            sortViewModel.startSorting()
                        }) {

                            Text(
                                text = "Start Sorting",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                    }
                }


            }


        }
    }
}


