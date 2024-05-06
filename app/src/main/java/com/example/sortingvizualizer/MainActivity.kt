package com.example.sortingvizualizer

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sortingvizualizer.BubbleSort.BubbleSortActivity
import com.example.sortingvizualizer.databinding.ActivityMainBinding
import java.util.Random


class MainActivity : AppCompatActivity() {

    class CustomItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.right = spacing
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GeneratedArrayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewGeneratedArray.layoutManager = layoutManager
        binding.recyclerViewGeneratedArray.addItemDecoration(CustomItemDecoration(8))

        adapter = GeneratedArrayAdapter(intArrayOf())
        binding.recyclerViewGeneratedArray.adapter = adapter

        binding.btnGenerateArray.setOnClickListener {

            generateArray()
        }

        binding.buttonVisualize.setOnClickListener {
            visualizeSorting()
        }


    }

    private fun visualizeSorting() {
        val selectedAlgorithm = binding.spinnerAlgo.selectedItem.toString()
        val randomArray = adapter.getArray()


        val intent = when (selectedAlgorithm) {
            "Bubble Sort" -> Intent(this, BubbleSortActivity::class.java)
//            "Insertion Sort" -> Intent(this, InsertionSortActivity::class.java)
//            "Merge Sort" -> Intent(this, MergeSortActivity::class.java)
//            "Quick Sort" -> Intent(this, QuickSortActivity::class.java)
            else -> null
        }

        if (intent != null) {
            intent.putExtra("randomArray", randomArray)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Algorithm not implemented", Toast.LENGTH_SHORT).show()
        }

    }

    private fun generateArray() {
        val arraySize = binding.etArraySize.text.toString().toIntOrNull()

        if (arraySize != null && arraySize > 0 && arraySize <= 20) {
            val randomArray = generateRandomArray(arraySize)
            adapter.updateArray(randomArray)
            binding.recyclerViewGeneratedArray.visibility = View.VISIBLE
            binding.buttonVisualize.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "Please enter a valid array size <=20", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateRandomArray(arraySize: Int): IntArray {
        val random = Random()
        val array = IntArray(arraySize) {
            random.nextInt(100)
        }

        return array
    }


}