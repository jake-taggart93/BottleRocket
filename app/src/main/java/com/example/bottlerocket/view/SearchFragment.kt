package com.example.bottlerocket.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottlerocket.MainActivity
import com.example.bottlerocket.data.model.City
import com.example.bottlerocket.data.model.CityResponse
import com.example.bottlerocket.databinding.CitySearchLayoutBinding
import com.example.bottlerocket.repository.PresentationData
import com.example.bottlerocket.util.showError
import com.example.bottlerocket.util.showLoading
import com.example.bottlerocket.view.adapter.CitySearchAdapter
import com.example.bottlerocket.viewmodel.CityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchFragment: Fragment() {

    private val viewModel: CityViewModel by inject()
    private lateinit var binding: CitySearchLayoutBinding

    private fun onSelectedCity(newCityPage: City){
        (activity as MainActivity).newCityPage(newCityPage.geonameid)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CitySearchLayoutBinding.inflate(inflater, container, false)

        initObservables()
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.searchCity.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getCityList(textView.text.toString())
                activity?.showLoading(true)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.closeSearch.setOnClickListener {
            activity?.supportFragmentManager?.
            beginTransaction()?.
            remove(this@SearchFragment)?.commit()
        }
    }

    private fun initObservables() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.cityList.collect {
                    when(it){
                        is PresentationData.ResponseCitySearch -> updateAdapter(it.data)
                        is PresentationData.Loading -> activity?.showLoading(it.isLoading)
                        is PresentationData.Error -> activity?.showError(it.errorMessage)
                    }
                }
            }
        }
    }

    private fun updateAdapter(data: CityResponse) {
        binding.cityList.layoutManager = LinearLayoutManager(context)
        binding.cityList.adapter = CitySearchAdapter(data.cities, ::onSelectedCity)
    }
}