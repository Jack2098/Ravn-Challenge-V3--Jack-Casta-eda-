package com.jack.ravn_challenge.ui.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jack.ravn_challenge.R
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.databinding.ItemPeopleBinding

class PruebaAdapter:PagingDataAdapter<PersonModel,PruebaAdapter.ViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_people,parent,false)
        return ViewHolder(inflater)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemPeopleBinding.bind(itemView)

        fun bind(person:PersonModel){
            binding.peopleName.text = person.name
            val species:String? = person.species?.name
            val homeworl:String = person.homeworld?.name!!
            if (species==null){
                binding.species.text = "Human from $homeworl"
            }else{
                binding.species.text = "$species from $homeworl"
            }
        }
    }

    class DiffUtilCallBack:DiffUtil.ItemCallback<PersonModel>(){
        override fun areItemsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
            return oldItem.id == newItem.id
        }

    }
}