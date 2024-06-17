package adaptadores

import adaptadores.BecerroAdapter.ViewHolderBecerro
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import interfaces.ListenerRecycleEvento
import uv.tc.cowtrol.R
import uv.tc.cowtrol.poko.Evento

class EventoAdapter (val evento:List<Evento>, val listenerRecycleEvento: ListenerRecycleEvento): RecyclerView.Adapter<EventoAdapter.ViewHolderEvento>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEvento {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_visualizar_potrero, parent, false)
        return ViewHolderEvento(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderEvento, position: Int) {
        val becerro = evento.get(position)
        holder
    }

    override fun getItemCount(): Int {
        return evento.size
    }

    class ViewHolderEvento(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNombreBecerro : TextView = itemView.findViewById(R.id.tv_nombre_becerro)
        val tvSiinigaBecerro : TextView = itemView.findViewById(R.id.tv_sinniga_becerro)
        val tvEditarBecerro : TextView = itemView.findViewById(R.id.tv_editar_becerro)
        val tvPotreroBecerro : TextView = itemView.findViewById(R.id.tv_potrero_becerro)
    }
}