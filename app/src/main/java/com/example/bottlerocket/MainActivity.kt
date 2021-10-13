package com.example.bottlerocket

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.*
import androidx.viewpager2.widget.ViewPager2
import com.example.bottlerocket.repository.PresentationData
import com.example.bottlerocket.util.showError
import com.example.bottlerocket.util.showLoading
import com.example.bottlerocket.view.SearchFragment
import com.example.bottlerocket.view.adapter.CitiesPageAdapter
import com.example.bottlerocket.viewmodel.CityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_FRAGMENT_SEARCH = "Fragment_Search"
    }

    private val viewModel: CityViewModel by inject()
    private val citiesAdapter: CitiesPageAdapter by lazy {
        CitiesPageAdapter(currentCityPageList, this@MainActivity)
    }
    private lateinit var viewPager: ViewPager2
    private var currentCityPageList = listOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            newCityPage(4054852)

        initViews()
        initObservables()
    }

    private fun initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        else
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

        setSupportActionBar(findViewById(R.id.city_toolbar))
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_icon_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = citiesAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.city_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> openSearchFragment()
            R.id.delete_city -> removeCurrentPage()
            R.id.radar_city -> Toast.makeText(
                this@MainActivity,
                "In progress",
                Toast.LENGTH_SHORT
            )
                .show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun removeCurrentPage() {
        viewModel.removeCityDetails(
            currentCityPageList[viewPager.currentItem]
        )
    }

    private fun openSearchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, SearchFragment(), KEY_FRAGMENT_SEARCH)
            .commit()
    }

    fun newCityPage(newCityPage: Int) {
        supportFragmentManager.findFragmentByTag(KEY_FRAGMENT_SEARCH)?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
        viewModel.insertCityPage(newCityPage)
    }

    private fun initObservables() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cityPage.collect {
                    when (it) {
                        is PresentationData.ResponseCityPageList -> updateCityPage(it.data)
                        is PresentationData.Error -> this@MainActivity.showError(it.errorMessage)
                        is PresentationData.Loading -> this@MainActivity.showLoading(it.isLoading)
                    }
                }
            }
        }
    }

    private fun updateCityPage(data: List<Int>) {
        currentCityPageList = data
        citiesAdapter.updateCityList(currentCityPageList)
        citiesAdapter.notifyDataSetChanged()
    }
}
