// SecondActivity.kt
package com.example.catfacts.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.catfacts.model.CatFact
import com.example.catfacts.network.RetrofitClient
import com.example.catfacts.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.BroadcastReceiver
import android.content.Context
import com.example.catfacts.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val catFact = it.getParcelableExtra<CatFact>(Constants.CAT_FACT_EXTRA)
                catFact?.let {
                    binding.textView.text = it.fact // Здесь используем binding
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(receiver, IntentFilter(Constants.ACTION_UPDATE_CAT_FACT))

        CoroutineScope(Dispatchers.IO).launch {
            val catFact = fetchData()
            sendBroadcast(Intent().apply {
                action = Constants.ACTION_UPDATE_CAT_FACT
                putExtra(Constants.CAT_FACT_EXTRA, catFact)
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private suspend fun fetchData(): CatFact {
        return withContext(Dispatchers.IO) {
            val apiService = RetrofitClient.create()
            apiService.getCatFact()
        }
    }
}
