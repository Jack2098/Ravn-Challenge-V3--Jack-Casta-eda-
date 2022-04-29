package com.jack.ravn_challenge.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jack.ravn_challenge.data.model.VehicleModel
import com.jack.ravn_challenge.databinding.FragmentDetailFavoriteBinding
import com.jack.ravn_challenge.ui.person.VehicleAdapter

class DetailFavoriteFragment : Fragment() {

    private var _binding:FragmentDetailFavoriteBinding? = null
    private val binding get() = _binding!!

    private val args:DetailFavoriteFragmentArgs by navArgs()

    private val vehicleAdapter by lazy { VehicleAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        val person = args.person
        binding.eyeColor.text = person.eyeColor!!.uppercase()
        binding.hairColor.text = person.hairColor!!.uppercase()
        binding.skinColor.text = person.skinColor!!.uppercase()
        binding.birthYear.text = person.birthYear!!.uppercase()
        Log.d("vehicles","${person.vehicleConnection?.vehicles!!}")
        if (person.vehicleConnection.vehicles.isNotEmpty()){
            val listVehicle = person.vehicleConnection.vehicles as MutableList
            vehicleAdapter.setList(listVehicle)
        }else{
            val noVehicle = VehicleModel("Does not have a vehicle")
            vehicleAdapter.setList(mutableListOf(noVehicle))
        }
    }

    private fun initRecyclerView(){
        binding.rvVehicle.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(),
                    DividerItemDecoration.VERTICAL)
            )
            adapter = vehicleAdapter
        }
    }
}