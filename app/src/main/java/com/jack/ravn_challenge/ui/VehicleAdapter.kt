package com.jack.ravn_challenge.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jack.ravn_challenge.R
import com.jack.ravn_challenge.data.model.VehicleModel
import com.jack.ravn_challenge.databinding.ItemVehicleBinding

class VehicleAdapter(private val context: Context):RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    val vehicleList: MutableList<VehicleModel> = mutableListOf()

    fun setList(list:MutableList<VehicleModel>){
        vehicleList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding = ItemVehicleBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater:View = LayoutInflater.from(context).inflate(R.layout.item_vehicle,parent,false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicle = vehicleList[position]
        holder.binding.vehicleName.text = vehicle.name
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }
}