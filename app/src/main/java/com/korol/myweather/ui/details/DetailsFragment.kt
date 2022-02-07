package com.korol.myweather.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.korol.myweather.app.App
import com.korol.myweather.databinding.FragmentDetailsBinding
import com.korol.myweather.ui.search.SearchViewModelFactory

class DetailsFragment : Fragment() {

    @javax.inject.Inject
    lateinit var vmFactory: DetailsViewModelFactory

    private lateinit var detailsViewModel: DetailsViewModel
    private var _binding: FragmentDetailsBinding? = null

    val args: DetailsFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity?.applicationContext as App).appComponent.injectDetailsFragment(this)
        
        detailsViewModel =
            ViewModelProvider(this,vmFactory).get(DetailsViewModel::class.java)

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val cityName = args.selectedCity
        detailsViewModel.initialWeatherInfo(cityName)

        val nameCity: TextView = binding.nameCity
        detailsViewModel.nameCity.observe(viewLifecycleOwner) {
            nameCity.text = it
        }

        val nameRegion: TextView = binding.nameRegion
        detailsViewModel.nameRegion.observe(viewLifecycleOwner){
            nameRegion.text = it
        }


        val textView: TextView = binding.textNotifications
        detailsViewModel.text.observe(viewLifecycleOwner){
            textView.text = it
        }

        val currentTemperature: TextView = binding.currentTemperature
        detailsViewModel.currentTemperature.observe(viewLifecycleOwner) {
            currentTemperature.text = it
        }

        val weatherVariable: TextView = binding.weatherVariable
        detailsViewModel.weatherVariable.observe(viewLifecycleOwner) {
            weatherVariable.text = it
        }

        val windSpeed: TextView = binding.windSpeed
        detailsViewModel.windSpeed.observe(viewLifecycleOwner) {
            windSpeed.text = it
        }

        val hourTemperature: TextView = binding.hourTemperature
        detailsViewModel.hourTemperature.observe(viewLifecycleOwner) {
            hourTemperature.text = it
        }

        val sixHourTemperature: TextView = binding.sixHourTemperature
        detailsViewModel.sixHourTemperature.observe(viewLifecycleOwner) {
            sixHourTemperature.text = it
        }

        val twelveHourTemperature: TextView = binding.twelveHourTemperature
        detailsViewModel.twelveHourTemperature.observe(viewLifecycleOwner) {
            twelveHourTemperature.text = it
        }

        val twentyFourHourTemperature: TextView = binding.twentyFourHourTemperature
        detailsViewModel.twentyFourHourTemperature.observe(viewLifecycleOwner) {
            twentyFourHourTemperature.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}