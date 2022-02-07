package com.korol.myweather.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.korol.myweather.R
import com.korol.myweather.app.App
import com.korol.myweather.databinding.FragmentSearchBinding
import com.korol.myweather.repository.ListItem

class SearchFragment : Fragment() {

    @javax.inject.Inject
    lateinit var vmFactory: SearchViewModelFactory

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var rvAdapter: RvAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity?.applicationContext as App).appComponent.injectSearchFragment(this)

        searchViewModel =
            ViewModelProvider(this, vmFactory).get(SearchViewModel::class.java)


        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSearch


        searchViewModel.citys.observe(viewLifecycleOwner) {
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.rcCityView.setLayoutManager(layoutManager)
            rvAdapter = RvAdapter(it, object : RVOnclickListener {
                override fun onClicked(city: ListItem) {

                    // переходим на экран деталей о погоде
                    val action =
                        SearchFragmentDirections.actionNavigationSearchToNavigationDetails(city)
                    Navigation.findNavController(root).navigate(action)
                }
            })
            binding.rcCityView.adapter = rvAdapter
            if (it == null) textView.text =
                getString(R.string.errorDbCitys) else textView.visibility = View.GONE
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                searchViewModel.fillAdapter(text!!)
                return true
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}