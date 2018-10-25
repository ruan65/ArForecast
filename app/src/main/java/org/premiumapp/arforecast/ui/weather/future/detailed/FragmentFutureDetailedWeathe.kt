package org.premiumapp.arforecast.ui.weather.future.detailed

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.premiumapp.arforecast.R

class FragmentFutureDetailedWeathe : Fragment() {

    companion object {
        fun newInstance() = FragmentFutureDetailedWeathe()
    }

    private lateinit var viewModel: FragmentFutureDetailedWeatheViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_future_detailed_weathe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentFutureDetailedWeatheViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
