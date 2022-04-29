package com.jack.ravn_challenge.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.data.model.toDomain
import com.jack.ravn_challenge.databinding.FragmentFavoriteBinding
import com.jack.ravn_challenge.ui.PeopleSWAdapter
import com.jack.ravn_challenge.ui.viewmodel.MainViewModel
import com.jack.ravn_challenge.vo.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : Fragment(), PeopleSWAdapter.OnItemClickListener {

    private var _binding:FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val peopleSWAdapter by lazy { PeopleSWAdapter(requireContext(),this) }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.favoritePeople().observe(viewLifecycleOwner, { data->
            when(data){
                is Resource.Loading->{}
                is Resource.Success->{
                    val response = data.data as MutableList<PersonModel>

                    peopleSWAdapter.setList(response)
                }
                is Resource.Failure->{}
            }
        })

    }

    private fun initRecyclerView(){
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(),
                    DividerItemDecoration.VERTICAL)
            )
            adapter = peopleSWAdapter
        }
    }

    override fun onPersonClick(person: PersonModel) {
        val personF = person.toDomain()
        peopleSWAdapter.removeList()
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFavoriteFragment(personF)
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}