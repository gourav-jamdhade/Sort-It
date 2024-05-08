package com.example.sortingvizualizer.BubbleSort

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sortingvizualizer.R
import com.example.sortingvizualizer.databinding.ActivityBubbleSortBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class BubbleSortActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBubbleSortBinding
    private lateinit var arrayAdapter: BubbleSortAdapter
    var isPaused = false
    private var sortingJob: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBubbleSortBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvArray.layoutManager = layoutManager
        arrayAdapter = BubbleSortAdapter(
            intArrayOf(),
            swapListener = this::swapAnimation,
            tvSwappingIndices = binding.tvSwappingIndices,
            tvLoop = binding.tvLoop
        )

        binding.rvArray.adapter = arrayAdapter

        val randomArray = intent.getIntArrayExtra("randomArray")
        randomArray?.let {
            arrayAdapter.updateArray(it)
        }

        binding.btnPause.setOnClickListener {
            isPaused = true
            binding.btnPause.visibility = View.GONE
            binding.btnPlay.visibility = View.VISIBLE
            pauseSorting()
        }

        binding.btnPlay.setOnClickListener {
            isPaused = false
            binding.btnPause.visibility = View.VISIBLE
            binding.btnPlay.visibility = View.GONE
            resumeSorting()
        }

        binding.btnStart.setOnClickListener {
            binding.btnPause.visibility = View.VISIBLE
            binding.btnPlay.visibility = View.VISIBLE
            binding.slider.visibility = View.VISIBLE
            startSorting(randomArray, 3000)
        }

        binding.btnJava.setOnClickListener {
            binding.btnJava.setTextColor(getColor(R.color.green))
            binding.btnCpp.setTextColor(getColor(R.color.orange))
            binding.btnPython.setTextColor(getColor(R.color.orange))
            binding.tvAlgorithmCode.setCode(getJavaCode().toString(), "java")
        }

        binding.btnCpp.setOnClickListener {
            binding.btnJava.setTextColor(getColor(R.color.orange))
            binding.btnCpp.setTextColor(getColor(R.color.green))
            binding.btnPython.setTextColor(getColor(R.color.orange))
            binding.tvAlgorithmCode.setCode(getCppCode().toString(), "cpp")

        }

        binding.btnPython.setOnClickListener {
            binding.btnJava.setTextColor(getColor(R.color.orange))
            binding.btnCpp.setTextColor(getColor(R.color.orange))
            binding.btnPython.setTextColor(getColor(R.color.green))
            binding.tvAlgorithmCode.setCode(getPythonCode().toString(), "py")
        }


        binding.slider.addOnChangeListener { slider, value, fromUser ->
            when (value.toInt()) {
                0 -> {
                    val delayTime = 3000
                    CoroutineScope(Dispatchers.Main).launch {
                        sortingJob?.cancel()
                        delay(500)
                        startSorting(arrayAdapter.getArray(), delayTime)
                        delay(500)
                    }


                }

                1 -> {
                    val delayTime = 2250
                    CoroutineScope(Dispatchers.Main).launch {
                        sortingJob?.cancel()
                        delay(500)
                        startSorting(arrayAdapter.getArray(), delayTime)
                        delay(500)
                    }
                }

                2 -> {
                    val delayTime = 1750

                    CoroutineScope(Dispatchers.Main).launch {
                        sortingJob?.cancel()
                        delay(1000)
                        startSorting(arrayAdapter.getArray(), delayTime)
                        delay(500)
                    }
                }
            }
        }
        binding.tvAlgorithmCode.setCode(getJavaCode().toString())
        binding.btnJava.setTextColor(getColor(R.color.green))
    }

    private fun pauseSorting() {
        isPaused = true
        sortingJob?.cancel()
    }


    private fun resumeSorting() {
        isPaused = false
        startSorting(arrayAdapter.getArray(), 3000)
    }


    private fun getPythonCode(): CharSequence? {

        return """
            # Python algorithm code for Bubble Sort
            
            def bubbleSort(arr):
                n = len(arr)
                for i in range(n-1):
                    for j in range(0, n-i-1):
                        if arr[j] > arr[j+1] :
                            arr[j], arr[j+1] = arr[j+1], arr[j]
                
            arr = [64, 34, 25, 12, 22, 11, 90]
            bubbleSort(arr)
            print ("Sorted array is:", arr)
        """.trimIndent()
    }

    private fun getCppCode(): CharSequence? {
        return """
            // C++ algorithm code for Bubble Sort
            #include <iostream>
            using namespace std;
            
            void bubbleSort(int arr[], int n) {
                for (int i = 0; i < n-1; i++) {
                    for (int j = 0; j < n-i-1; j++) {
                        if (arr[j] > arr[j+1]) {
                            // Swap arr[j] and arr[j+1]
                            int temp = arr[j];
                            arr[j] = arr[j+1];
                            arr[j+1] = temp;
                        }
                    }
                }
            }
        """.trimIndent()
    }

    private fun getJavaCode(): CharSequence? {

        return """ 
             // Java algorithm code for Bubble Sort
            public class BubbleSort {
                public static void bubbleSort(int[] arr) {
                    int n = arr.length;
                    for (int i = 0; i < n-1; i++) {
                        for (int j = 0; j < n-i-1; j++) {
                            if (arr[j] > arr[j+1]) {
                                // Swap arr[j] and arr[j+1]
                                int temp = arr[j];
                                arr[j] = arr[j+1];
                                arr[j+1] = temp;
                            }
                        }
                    }
                }
            }
        """.trimIndent()

    }

    private fun startSorting(randomArray: IntArray?, delayTime: Int) {
        var n = randomArray!!.size
        sortingJob = CoroutineScope(Dispatchers.Default).launch {
            for (i in 0 until n - 1) {
                for (j in 0 until n - i - 1) {


                    if (randomArray[j] > randomArray[j + 1]) {


                        withContext(Dispatchers.Main) {
                            arrayAdapter.swapItems(
                                binding.rvArray,
                                index1 = j,
                                index2 = j + 1,
                                loop = i,
                                loopOuter = j,
                                delayTime = delayTime.toLong()
                            )
                        }

                        Log.d("element", "${randomArray[j]}, ${randomArray[j + 1]}")
                        delay(delayTime.toLong())
                    }
                }
            }
        }
    }

    private fun swapAnimation(index1: Int, index2: Int) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.swap_animation)
        animation.duration = 300L
        binding.rvArray.findViewHolderForAdapterPosition(index1)?.itemView?.startAnimation(animation)
        binding.rvArray.findViewHolderForAdapterPosition(index2)?.itemView?.startAnimation(animation)
    }

}