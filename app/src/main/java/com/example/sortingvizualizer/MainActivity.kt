package com.example.sortingvizualizer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sortingvizualizer.databinding.ActivityMainBinding
import java.util.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GeneratedArrayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewGeneratedArray.layoutManager = layoutManager

        adapter = GeneratedArrayAdapter(intArrayOf())
        binding.recyclerViewGeneratedArray.adapter = adapter

        binding.btnGenerateArray.setOnClickListener {
            generateArray()
        }

        binding.buttonVisualize.setOnClickListener {
            vizualizeSorting()
        }


    }

    private fun vizualizeSorting() {
        val selectedAlgorithm = binding.spinnerAlgo.selectedItem.toString()
        val randomArray = adapter.getArray()

        when(selectedAlgorithm){
            "Bubble Sort" -> bubbleSort(randomArray)
        }
    }

    private fun generateArray() {
        val arraySize = binding.etArraySize.text.toString().toIntOrNull()

        if (arraySize != null && arraySize > 0 && arraySize <= 20) {
            val randomArray = generateRandomArray(arraySize)
            adapter.updateArray(randomArray)
            binding.buttonVisualize.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "Please enter a valid array size <=20", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateRandomArray(arraySize: Int): IntArray {
        val random = Random()
        val array = IntArray(arraySize){
            random.nextInt(100)
        }

        return array
    }
}