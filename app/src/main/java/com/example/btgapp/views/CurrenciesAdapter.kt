package com.example.btgapp.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.btgapp.databinding.RvCurrencyItemBinding
import com.example.domain.model.Currency

class CurrenciesAdapter : RecyclerView.Adapter<CurrenciesAdapter.CurrenciesViewHolder>() {

    var currencies: List<Currency> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesViewHolder =
        CurrenciesViewHolder(
            RvCurrencyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {
        holder.bind(
            currencies[position]
        )
    }

    override fun getItemCount(): Int = currencies.size

    inner class CurrenciesViewHolder(private val binding: RvCurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: Currency) {
            binding.currency = currency
        }
    }
}