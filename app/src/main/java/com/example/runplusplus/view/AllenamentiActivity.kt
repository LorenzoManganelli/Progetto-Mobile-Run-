package com.example.runplusplus.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runplusplus.R
import com.example.runplusplus.adapter.AllenamentoAdapter
import com.example.runplusplus.databinding.ActivityAllenamentiBinding
import com.example.runplusplus.database.AppDatabase
import com.example.runplusplus.repository.AllenamentoRepository
import com.example.runplusplus.viewmodel.AllenamentoViewModel
import com.example.runplusplus.viewmodel.AllenamentoViewModelFactory

class AllenamentiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllenamentiBinding
    private lateinit var allenamentoViewModel: AllenamentoViewModel
    private lateinit var adapter: AllenamentoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllenamentiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inizializza il ViewModel
        val database = AppDatabase.getDatabase(this)
        val allenamentoDao = database.allenamentoDao()
        val repository = AllenamentoRepository(allenamentoDao)
        allenamentoViewModel = ViewModelProvider(this, AllenamentoViewModelFactory(repository)).get(AllenamentoViewModel::class.java)

        setupRecyclerView()
        observeAllenamenti()
    }


    private fun setupRecyclerView() {
        adapter = AllenamentoAdapter(emptyList()) // Inizializza con una lista vuota
        binding.recyclerViewAllenamenti.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewAllenamenti.adapter = adapter
    }

    private fun observeAllenamenti() {
        allenamentoViewModel.allAllenamenti.observe(this, Observer { allenamenti ->
            Log.d("AllenamentiActivity", "Allenamenti ricevuti: ${allenamenti.size}")
            adapter = AllenamentoAdapter(allenamenti)
            binding.recyclerViewAllenamenti.adapter = adapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.allenamenti_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ordina_data -> {
                allenamentoViewModel.ordinaPerData()
                return true
            }
            R.id.ordina_tipo -> {
                allenamentoViewModel.ordinaPerTipo()
                return true
            }
            R.id.ordina_nome -> {
                allenamentoViewModel.ordinaPerNome()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}









