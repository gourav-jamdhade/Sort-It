package com.example.sortingvizualizer.BubbleSort

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sortingvizualizer.R
import kotlin.math.max

class BubbleSortAdapter(
    private var array: IntArray,
    private var swapIndices: Pair<Int, Int>? = null,
    private val swapListener: (Int, Int) -> Unit,
    private val tvSwappingIndices: TextView,
    private val tvLoop: TextView
) :
    RecyclerView.Adapter<BubbleSortAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
        val textViewIndex: TextView = itemView.findViewById(R.id.textViewIndex)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_tem_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewItem.text = array[position].toString()
        holder.textViewIndex.text = position.toString()

        swapIndices?.let { (firstIndex, secondIndex) ->
            if (position == firstIndex || position == secondIndex) {
                holder.textViewItem.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.highlight_color
                    )
                )
            } else {
                holder.textViewItem.setBackgroundColor(Color.TRANSPARENT)
            }
        } ?: run {
            // If swapIndices is null, set background color to transparent
            holder.textViewItem.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun getItemCount(): Int {
        return array.size


    }

    fun getArray(): IntArray {
        return array
    }

    fun updateArray(newArray: IntArray) {
        array = newArray
        notifyDataSetChanged()
    }

    fun swapItems(recyclerView: RecyclerView, index1: Int, index2: Int, loop: Int, loopOuter: Int, delayTime:Long) {
        val temp = array[index1]
        array[index1] = array[index2]
        array[index2] = temp

        val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

        if (index1 < firstVisiblePosition || index1 > lastVisiblePosition ||
            index2 < firstVisiblePosition || index2 > lastVisiblePosition
        ) {
            // Scroll RecyclerView to bring swapping elements into view
            val scrollToPosition = max(index1, index2)
            recyclerView.scrollToPosition(scrollToPosition)
        }
        swapIndices = Pair(index1, index2)
        notifyItemChanged(index1)
        notifyItemChanged(index2)

        swapListener(index1, index2)

        // Reset swapIndices to null after animation
        Handler(Looper.getMainLooper()).postDelayed({
            swapIndices = null
            notifyItemChanged(index1)
            notifyItemChanged(index2)
        }, delayTime.toLong())

        tvSwappingIndices.text = "Swapping indices: i = $index1, j = $index2"
        tvLoop.text = "Loop Count: $loop, $loopOuter"

    }
}