package com.example.bottlerocket.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bottlerocket.data.model.City

class CitySearchAdapter(
    private val dataSet: List<City>,
    private val selectedCity: (City) -> Unit
) :
    RecyclerView.Adapter<CitySearchAdapter.CitySearchViewHolder>() {

    class CitySearchViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val text: TextView = view.findViewById(android.R.id.text1)

        fun onBind(dataItem: City, selectCity: (City) -> Unit) {
            text.text = dataItem.name
            view.setOnClickListener { selectCity(dataItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySearchViewHolder {
        return CitySearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                android.R.layout.simple_list_item_1,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CitySearchViewHolder, position: Int) {
        holder.onBind(dataSet[position], selectedCity)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}