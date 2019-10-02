package com.example.weatherapp.ui.nextDays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.NextDaysFragmentBinding
import com.example.weatherapp.di.ViewModelInjectionFactory
import com.example.weatherapp.ui.MainActivityViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.next_days_fragment.*
import javax.inject.Inject

class NextDaysFragment : Fragment() {

    lateinit var nextDaysFragmentBinding: NextDaysFragmentBinding
    lateinit var viewModel: NextDaysViewModel

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<NextDaysViewModel>

    lateinit var mainViewModel: MainActivityViewModel

    @Inject
    lateinit var adapter: NextDaysAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nextDaysFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.next_days_fragment, container, false)
        val view = nextDaysFragmentBinding.root
        nextDaysFragmentBinding.lifecycleOwner = this
        AndroidSupportInjection.inject(this)
        nextDaysFragmentBinding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this, viewModelInjectionFactory)
            .get(NextDaysViewModel::class.java)
        mainViewModel = activity?.run {
            ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        } ?: throw Exception(getString(R.string.invalidActivity))
        mainViewModel.weatherLocationLiveData.observe(this, Observer {
            val lat = it.latitude
            val lng = it.longitude
            viewModel.getFiveDaysWeather(lat, lng)
        })
        nextDaysFragmentBinding.viewModel = viewModel

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextDaysFragmentBinding.recyclerView.layoutManager =
            LinearLayoutManager(recyclerView.context)
        nextDaysFragmentBinding.recyclerView.adapter = adapter
        registerObservers()


    }

    fun registerObservers(){
        viewModel.fiveDaysWeatherLiveData.observe(this, Observer { weather ->
            adapter.setWeatherList(weather.list)
        })
    }

    companion object {
        fun newInstance(): NextDaysFragment {
            return NextDaysFragment()
        }
    }


}
