package com.example.runplusplus.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.runplusplus.model.Allenamento
import com.example.runplusplus.repository.AllenamentoRepository
import kotlinx.coroutines.launch

class AllenamentoViewModel(private val repository: AllenamentoRepository) : ViewModel() {

    private val _allAllenamenti = MutableLiveData<List<Allenamento>>()
    val allAllenamenti: LiveData<List<Allenamento>> = _allAllenamenti

    init {
        loadAllAllenamenti()
        verifyDatabaseContent() // Aggiungi questa chiamata
    }

    private fun loadAllAllenamenti() {
        viewModelScope.launch {
            repository.allAllenamenti.collect { allenamenti ->
                _allAllenamenti.value = allenamenti
            }
        }
    }

    // Funzione per verificare il contenuto del database
    private fun verifyDatabaseContent() {
        viewModelScope.launch {
            val allenamenti = repository.getAllAllenamentiSync()
            Log.d("AllenamentoViewModel", "Allenamenti dal database: ${allenamenti.size}")
        }
    }

    fun ordinaPerData() {
        viewModelScope.launch {
            repository.getAllAllenamentiOrderByData().collect { allenamenti ->
                _allAllenamenti.value = allenamenti
            }
        }
    }

    fun ordinaPerTipo() {
        viewModelScope.launch {
            repository.getAllAllenamentiOrderByTipo().collect { allenamenti ->
                _allAllenamenti.value = allenamenti
            }
        }
    }

    fun ordinaPerNome() {
        viewModelScope.launch {
            repository.getAllAllenamentiOrderByNome().collect { allenamenti ->
                _allAllenamenti.value = allenamenti
            }
        }
    }

    fun insert(allenamento: Allenamento) = viewModelScope.launch {
        repository.insert(allenamento)
    }

    fun delete(allenamento: Allenamento) = viewModelScope.launch {
        repository.delete(allenamento)
    }

    fun update(allenamento: Allenamento) = viewModelScope.launch {
        repository.update(allenamento)
    }

    fun getAllenamentiByTipo(tipo: String): LiveData<List<Allenamento>> {
        return repository.getAllenamentiByTipo(tipo).asLiveData()
    }

    fun getAllenamentoById(id: Int): LiveData<Allenamento> {
        return repository.getAllenamentoById(id).asLiveData()
    }
}

class AllenamentoViewModelFactory(private val repository: AllenamentoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllenamentoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AllenamentoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}