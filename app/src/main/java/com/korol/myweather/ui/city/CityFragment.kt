package com.korol.myweather.ui.city

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.korol.myweather.R
import com.korol.myweather.app.App
import com.korol.myweather.databinding.FragmentCityBinding
import com.korol.myweather.db.ViewedCity
import com.korol.myweather.repository.ListItem
import com.korol.myweather.ui.search.RVOnclickListener
import com.korol.myweather.ui.search.RvAdapter
import com.korol.myweather.ui.search.SearchFragmentDirections
import com.korol.myweather.ui.search.SearchViewModelFactory

class CityFragment : Fragment() {

    @javax.inject.Inject
    lateinit var vmFactory: CityViewModelFactory

    private lateinit var cityViewModel: CityViewModel
    private var _binding: FragmentCityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var rvViewedCityAdapter: RvViewedCityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity?.applicationContext as App).appComponent.injectCityFragment(this)
        cityViewModel =
            ViewModelProvider(this,vmFactory).get(CityViewModel::class.java)

        _binding = FragmentCityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCity
        cityViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        cityViewModel.viewedCitys.observe(viewLifecycleOwner) {
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.rcViewedCity.setLayoutManager(layoutManager)
            rvViewedCityAdapter = RvViewedCityAdapter(it, object : RVViewedCityOnClickListener {
                override fun onClicked(city: ViewedCity) {
                    val cityLI = ListItem()
                    cityLI.id = city.id
                    cityLI.city = city.nameCity
                    cityLI.typeRegion = city.nameRegion
                    cityLI.region = city.nameRegion
                    cityLI.latitude = city.latitude
                    cityLI.longitude = city.longitude
                    // переходим на экран деталей о погоде
                    val action =
                        CityFragmentDirections.actionNavigationCityToNavigationDetails(cityLI)
                    Navigation.findNavController(root).navigate(action)
                }

                override fun onClickedBasket(city: ViewedCity) {
                    cityViewModel.deleteCity(city)
                }
            })
            binding.rcViewedCity.adapter = rvViewedCityAdapter
            if (it == null) textView.text =
                getString(R.string.errorViewedDbCitys) else textView.visibility = View.GONE
            if (it.size == 0) {
                textView.visibility = View.VISIBLE
                textView.text = getString(R.string.DBViewedCityEmpty)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}