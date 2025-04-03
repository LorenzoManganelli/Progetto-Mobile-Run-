package com.example.runplusplus.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.runplusplus.R
import com.example.runplusplus.model.Allenamento

class AllenamentoAdapter(private val allenamenti: List<Allenamento>) :
    RecyclerView.Adapter<AllenamentoAdapter.AllenamentoViewHolder>() {

    class AllenamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dataTextView: TextView = itemView.findViewById(R.id.dataTextView)
        val tipoTextView: TextView = itemView.findViewById(R.id.tipoTextView)
        val durataTextView: TextView = itemView.findViewById(R.id.durataTextView)
        val calorieTextView: TextView = itemView.findViewById(R.id.calorieTextView)
        val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllenamentoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.allenamento_item, parent, false)
        return AllenamentoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AllenamentoViewHolder, position: Int) {
        val allenamento = allenamenti[position]
        holder.dataTextView.text = allenamento.data
        holder.tipoTextView.text = allenamento.tipo
        holder.durataTextView.text = allenamento.durata.toString()
        holder.calorieTextView.text = allenamento.calorieBruciate.toString()
        holder.noteTextView.text = allenamento.note
    }

    override fun getItemCount(): Int {
        Log.d("AllenamentoAdapter", "getItemCount: ${allenamenti.size}")
        return allenamenti.size
    }
}