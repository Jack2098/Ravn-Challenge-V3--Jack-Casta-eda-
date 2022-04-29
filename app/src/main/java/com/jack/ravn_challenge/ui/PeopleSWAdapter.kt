package com.jack.ravn_challenge.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jack.ravn_challenge.R
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.databinding.ItemPeopleBinding

class PeopleSWAdapter(private val context: Context,
                      private val itemClickListener: OnItemClickListener
                      ):RecyclerView.Adapter<PeopleSWAdapter.ViewHolder>() {

    var onEndOfListReached: (() -> Unit)? = null
    val peopleList:MutableList<PersonModel> = mutableListOf()

    fun setList(newList: MutableList<PersonModel>){
        peopleList.addAll(newList)
        notifyDataSetChanged()
    }

    fun removeList(){
        peopleList.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onPersonClick(person:PersonModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater:View = LayoutInflater.from(context).inflate(R.layout.item_people,parent,false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = peopleList[position]
        holder.binding.peopleName.text = person.name
        val species:String? = person.species?.nameSM
        val homeworl:String = person.homeworld?.nameHM!!
        if (species==null){
            holder.binding.species.text = "Human from $homeworl"
        }else{
            holder.binding.species.text = "$species from $homeworl"
        }
        if (position == peopleList.size - 1) {
            onEndOfListReached?.invoke()
        }
        holder.binding.root.setOnClickListener {
            itemClickListener.onPersonClick(person)
        }
    }

    override fun getItemCount(): Int = peopleList.size


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding = ItemPeopleBinding.bind(itemView)
    }
}