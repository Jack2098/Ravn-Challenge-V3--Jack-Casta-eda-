package com.jack.ravn_challenge.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jack.ravn_challenge.databinding.FragmentPeopleSWBinding
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.ui.viewmodel.MainViewModel
import com.jack.ravn_challenge.vo.Resource

class PeopleSWFragment : Fragment(),PeopleSWAdapter.OnItemClickListener {

    private var _binding:FragmentPeopleSWBinding? = null
    private val binding get() = _binding!!

    private val peopleSWAdapter by lazy { PeopleSWAdapter(requireContext(),this) }

    private val peopleViewModel: MainViewModel by viewModels()

    private lateinit var list:MutableList<PersonModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPeopleSWBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = mutableListOf()

        val count = 5


        initRecyclerView()

        Log.d("people","asas------")
        setupObserver(count)

        binding.refresh.setOnRefreshListener {
            peopleSWAdapter.removeList()
            peopleViewModel.peopleModel.removeObservers(viewLifecycleOwner)
            peopleViewModel.peopleModel.value=Resource.Success(null)

            setupObserver(count)

            binding.refresh.isRefreshing = false
        }

    }

    private fun initRecyclerView(){
        binding.rvPeople.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
            adapter = peopleSWAdapter
        }
    }



    fun setupObserver(count:Int){
        peopleViewModel.getAllPeople("",count)

        peopleViewModel.peopleModel.observe(viewLifecycleOwner, Observer {  allPeople->
            when(allPeople){
                is Resource.Loading->{
                    binding.rvPeople.visibility = View.GONE
                    binding.error.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success->{
                    binding.rvPeople.visibility = View.VISIBLE
                    binding.error.visibility = View.GONE
                    //Log.d("people","${allPeople.data.people}")
                    Log.d("people","${allPeople.data}")
                    if (allPeople.data != null){

                        val personList = (allPeople.data?.people!!)

                        peopleSWAdapter.setList(personList as MutableList<PersonModel>)
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

    /*fun getData(){
        val people = mutableListOf<GetAllPeopleQuery.Person>()
        adapter = PeopleSWAdapter(requireContext(),people)
        binding.rvPeople.adapter = adapter

        val apolloClient = GraphQLInstance.get()

        val channel = Channel<Unit>(Channel.CONFLATED)

        // Send a first item to do the initial load else the list will stay empty forever
        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }

        lifecycleScope.launchWhenResumed {

            binding.refresh.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.error.visibility = View.GONE

            var cursor:String? = null
            for (item in channel){

                val response = try {
                    apolloClient.query(GetAllPeopleQuery(cursor = Optional.presentIfNotNull(cursor),count = Optional.presentIfNotNull(5))).execute()
                }catch (e:ApolloException){
                    binding.progressBar.visibility = View.GONE
                    binding.error.visibility = View.VISIBLE
                    return@launchWhenResumed
                }
                Handler().postDelayed(Runnable {
                    val newpeople = response.data?.allPeople?.people?.filterNotNull()
                    if (newpeople != null){
                        binding.refresh.visibility = View.VISIBLE
                        people.addAll(newpeople)
                        Log.d("newpeople","$newpeople")
                        Log.d("people","$people")
                        adapter.notifyDataSetChanged()
                    }
                    cursor = response.data?.allPeople?.pageInfo?.endCursor


                    binding.refresh.setOnRefreshListener{
                        Handler().postDelayed(Runnable {
                            binding.refresh.isRefreshing = false
                        }, 1000)
                    }
                }, 2000)
                if (response.data?.allPeople?.pageInfo?.hasNextPage!= true){
                    binding.progressBar.visibility = View.GONE
                    break
                }
            }
        }
    }*/
}