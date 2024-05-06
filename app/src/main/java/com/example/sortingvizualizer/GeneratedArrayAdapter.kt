package com.example.sortingvizualizer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GeneratedArrayAdapter(private var array: IntArray) :
    RecyclerView.Adapter<GeneratedArrayAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GeneratedArrayAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_tem_view, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GeneratedArrayAdapter.ViewHolder, position: Int) {
        holder.bind(array[position],position)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    fun updateArray(newArray:IntArray){
        array = newArray
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
        private val textViewIndex: TextView = itemView.findViewById(R.id.textViewIndex)

        fun bind(item: Int, index:Int) {
            textViewItem.text = item.toString()
            textViewIndex.text = index.toString()

        }
    }

    fun getArray():IntArray{
        return array
    }
}