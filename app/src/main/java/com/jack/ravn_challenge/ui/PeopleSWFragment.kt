package com.jack.ravn_challenge.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jack.ravn_challenge.R
import com.jack.ravn_challenge.databinding.FragmentPeopleSWBinding
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.ui.viewmodel.MainViewModel
import com.jack.ravn_challenge.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleSWFragment : Fragment(),PeopleSWAdapter.OnItemClickListener,SearchView.OnQueryTextListener {

    private var _binding:FragmentPeopleSWBinding? = null
    private val binding get() = _binding!!

    private val peopleSWAdapter by lazy { PeopleSWAdapter(requireContext(),this) }

    private val peopleViewModel: MainViewModel by viewModels()

    private lateinit var list:MutableList<PersonModel>

    private var flagSearch = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleSWBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = mutableListOf()


        initRecyclerView()
        setupObserver()

        binding.refresh.setOnRefreshListener {
            peopleSWAdapter.removeList()
            peopleViewModel.peopleModel.removeObservers(viewLifecycleOwner)
            peopleViewModel.peopleModel.value=Resource.Success(null)

            setupObserver()
            binding.searchPerson.visibility = View.GONE

            binding.refresh.isRefreshing = false
        }

        binding.searchPerson.setOnQueryTextListener(this)

    }

    private fun initRecyclerView(){
        binding.rvPeople.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
            adapter = peopleSWAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorite,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favorite->{
                val action = PeopleSWFragmentDirections.actionPeopleSWFragmentToFavoriteFragment()
                findNavController().navigate(action)
                return true
            }
            /*R.id.search->{
                if(flagSearch){
                    binding.searchPerson.visibility = View.VISIBLE
                    flagSearch = false
                }else{
                    binding.searchPerson.visibility = View.GONE
                    flagSearch = true
                }
                return true
            }*/
            else->super.onOptionsItemSelected(item)
        }
    }

    private fun setupObserver(){
        val count = 5
        peopleViewModel.getAllPeople("",count)

        peopleViewModel.peopleModel.observe(viewLifecycleOwner, {  allPeople->
            when(allPeople){
                is Resource.Loading->{
                    binding.rvPeople.visibility = View.GONE
                    binding.error.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success->{
                    binding.rvPeople.visibility = View.VISIBLE
                    binding.error.visibility = View.GONE
                    if (allPeople.data != null){

                        val personList = (allPeople.data.people!!)

                        peopleSWAdapter.setList(personList as MutableList<PersonModel>)

                        if(!allPeople.data.pageInfo?.hasNextPage!!){
                            binding.progressBar.visibility = View.GONE
                            binding.searchPerson.visibility = View.VISIBLE
                        }
                    }
                }
                is Resource.Failure->{
                    binding.rvPeople.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.error.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onPersonClick(person: PersonModel) {
        val action = PeopleSWFragmentDirections.actionPeopleSWFragmentToPersonFragment(person.name!!,person.id!!)
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        peopleSWAdapter.fiterPerson(newText)
        return false
    }
}