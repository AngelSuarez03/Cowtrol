package uv.tc.cowtrol.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uv.tc.cowtrol.interfaces.ListenerRecycleBecerros
import uv.tc.cowtrol.R
import uv.tc.cowtrol.poko.Becerro

class BecerroAdapter (val becerros: List<Becerro>, val listenerRecycleBecerros: ListenerRecycleBecerros): RecyclerView.Adapter<BecerroAdapter.ViewHolderBecerro>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBecerro {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_becerros, parent, false)
        return ViewHolderBecerro(itemView)
    }

    override fun getItemCount(): Int {
        return becerros.size
    }

    override fun onBindViewHolder(holder: ViewHolderBecerro, position: Int) {
        val becerro = becerros.get(position)
        holder.tvNombreBecerro.text = becerro.nombre
        holder.tvSiinigaBecerro.text = becerro.siiniga.toString()
        holder.tvPotreroBecerro.text = becerro.potrero

        holder.tvEditarBecerro.setOnClickListener{
            listenerRecycleBecerros.clicEditarBecerro(becerro, position)
        }
    }


    class ViewHolderBecerro(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNombreBecerro : TextView = itemView.findViewById(R.id.tv_nombre_becerro)
        val tvSiinigaBecerro : TextView = itemView.findViewById(R.id.tv_sinniga_becerro)
        val tvEditarBecerro : TextView = itemView.findViewById(R.id.tv_editar_becerro)
        val tvPotreroBecerro : TextView = itemView.findViewById(R.id.tv_potrero_becerro)
    }
}