package com.jack.ravn_challenge.ui.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jack.ravn_challenge.data.model.VehicleModel
import com.jack.ravn_challenge.databinding.FragmentPersonBinding
import com.jack.ravn_challenge.ui.viewmodel.MainViewModel
import com.jack.ravn_challenge.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : Fragment() {

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    val args: PersonFragmentArgs by navArgs()

    private val vehicleAdapter by lazy { VehicleAdapter(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.getPerson(id = args.idPerson)

        setupObserver()

    }

    private fun setupObserver(){
        viewModel.personModel.observe(viewLifecycleOwner,  { data->
            when(data){
                is Resource.Loading->{}
                is Resource.Success->{
                    DataVisible()
                    val person = data.data
                    binding.eyeColor.text = person.eyeColor!!.uppercase()
                    binding.hairColor.text = person.hairColor!!.uppercase()
                    binding.skinColor.text = person.skinColor!!.uppercase()
                    binding.birthYear.text = person.birthYear!!.uppercase()

                    binding.btnFavorite.setOnClickListener {
                        viewModel.saveFavoritePerson(data.data)
                        Toast.makeText(requireContext(),"Favorite person was saved",Toast.LENGTH_SHORT).show()
                    }

                    if (person.vehicleConnection?.vehicles!!.isNotEmpty()){
                        val listVehicle = person.vehicleConnection.vehicles as MutableList
                        vehicleAdapter.setList(listVehicle)
                    }else{
                        val noVehicle = VehicleModel("Does not have a vehicle")
                        vehicleAdapter.setList(mutableListOf(noVehicle))
                    }
                }
                is Resource.Failure->ErrorVisible()
            }
        })
    }
    private fun DataVisible(){
        binding.detailPerson.visibility = View.VISIBLE
        binding.prograssBar.visibility = View.GONE
    }
    private fun ErrorVisible(){
        binding.rvVehicle.visibility = View.GONE
        binding.prograssBar.visibility = View.GONE
        binding.error.visibility = View.VISIBLE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}