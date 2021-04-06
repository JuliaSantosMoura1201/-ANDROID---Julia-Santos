package com.example.btgapp.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.btgapp.R
import com.example.btgapp.databinding.ActivityMainBinding
import com.example.btgapp.extensions.observe
import com.example.btgapp.extensions.observeSingleEvent
import com.example.presentation.viewmodels.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding

    private val adapter: CurrenciesAdapter by lazy {
        CurrenciesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        initViews()
        inscribeObservers()
    }

    private fun initViews(){
        binding.rvCurrencies.setHasFixedSize(true)
        binding.rvCurrencies.adapter = adapter
    }
    private fun inscribeObservers(){
        observe(viewModel.currencies){
            adapter.currencies = it
        }
        observeSingleEvent(viewModel.goToCurrencyConverterActivity){
            startActivity(Intent(this, CurrencyConverterActivity::class.java))
        }
        observeSingleEvent(viewModel.error){
            Toast.makeText(this, getString(R.string.txt_error), Toast.LENGTH_LONG).show()
        }
        observeSingleEvent(viewModel.noNetwork){
            Toast.makeText(this, getString(R.string.txt_no_network), Toast.LENGTH_LONG).show()
        }
    }
}