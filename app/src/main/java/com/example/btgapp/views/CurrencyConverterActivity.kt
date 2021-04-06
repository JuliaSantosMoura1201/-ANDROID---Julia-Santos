package com.example.btgapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.btgapp.R
import com.example.btgapp.databinding.ActivityCurrencyConverterBinding
import com.example.btgapp.extensions.observe
import com.example.btgapp.extensions.observeSingleEvent
import com.example.presentation.viewmodels.CurrencyConverterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyConverterBinding
    private val viewModel by viewModel<CurrencyConverterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency_converter)
        binding.viewModel = viewModel
        inscribeObservers()
    }

    private fun inscribeObservers(){
        observe(viewModel.convertResult){
            binding.txtResult.text = it
        }
        observeSingleEvent(viewModel.error){
            Toast.makeText(this, getString(R.string.txt_error), Toast.LENGTH_LONG).show()
        }
        observeSingleEvent(viewModel.noNetwork){
            Toast.makeText(this, getString(R.string.txt_no_network), Toast.LENGTH_LONG).show()
        }
    }
}