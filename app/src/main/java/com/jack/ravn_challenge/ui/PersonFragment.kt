package com.jack.ravn_challenge.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jack.ravn_challenge.R
import com.jack.ravn_challenge.data.model.VehicleModel
import com.jack.ravn_challenge.databinding.FragmentPersonBinding
import com.jack.ravn_challenge.ui.viewmodel.MainViewModel
import com.jack.ravn_challenge.vo.Resource

class PersonFragment : Fragment() {

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    private val personViewModel: MainViewModel by viewModels()

    val args: PersonFragmentArgs by navArgs()

    private val vehicleAdapter by lazy { VehicleAdapter(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        personViewModel.getPerson(id = args.idPerson)

        personViewModel.personModel.observe(viewLifecycleOwner, Observer {data->
            when(data){
                is Resource.Loading->{
                    binding.detailPerson.visibility = View.GONE
                    binding.error.visibility = View.GONE
                }
                is Resource.Success->{
                    binding.detailPerson.visibility = View.VISIBLE
                    binding.prograssBar.visibility = View.GONE
                    val person = data.data
                    binding.eyeColor.text = person.eyeColor!!.uppercase()
                    binding.hairColor.text = person.hairColor!!.uppercase()
                    binding.skinColor.text = person.skinColor!!.uppercase()
                    binding.birthYear.text = person.birthYear!!.uppercase()
                    Log.d("vehicles","${person.vehicleConnection?.vehicles}")
                    if (person.vehicleConnection?.vehicles!!.isNotEmpty()){
                        val listVehicle = person.vehicleConnection.vehicles as MutableList
                        vehicleAdapter.setList(listVehicle)
                    }else{
                        val noVehicle = VehicleModel("Does not have a vehicle")
                        vehicleAdapter.setList(mutableListOf(noVehicle))
                    }
                }
                is Resource.Failure->{
                    binding.rvVehicle.visibility = View.GONE
                    binding.prograssBar.visibility = View.GONE
                    binding.error.visibility = View.VISIBLE
                }
            }
        })

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