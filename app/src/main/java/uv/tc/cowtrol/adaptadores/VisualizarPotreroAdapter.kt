package uv.tc.cowtrol.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uv.tc.cowtrol.interfaces.ListenerRecycleBecerros
import uv.tc.cowtrol.R
import uv.tc.cowtrol.poko.Becerro

class VisualizarPotreroAdapter (val becerros:List<Becerro>, val listenerRecycleBecerros: ListenerRecycleBecerros): RecyclerView.Adapter<VisualizarPotreroAdapter.ViewHolderBecerro>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBecerro {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_visualizar_potrero, parent, false)
        return ViewHolderBecerro(itemView)
    }

    override fun onBindViewHolder(holderBecerro: ViewHolderBecerro, position: Int) {
        val becerro = becerros.get(position)
        holderBecerro.tv_sinniga.text= becerro.siiniga.toString()
        holderBecerro.tv_edad.text=becerro.edad.toString()
        holderBecerro.tv_nombre.text=becerro.nombre
        holderBecerro.iv_imagen.setImageResource(R.drawable.vaca)
        holderBecerro.iv_icono.setImageResource(R.drawable.calendar_plus)
        holderBecerro.iv_icono.setOnClickListener{
            listenerRecycleBecerros.clicEditarBecerro(becerro, position)
        }
    }

    override fun getItemCount(): Int {
        return becerros.size
    }

    class ViewHolderBecerro(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tv_sinniga: TextView = itemView.findViewById(R.id.tv_siniiga_visualizar_potrero)
        val tv_edad: TextView = itemView.findViewById(R.id.tv_edad_visualizar_potrero)
        val tv_nombre: TextView = itemView.findViewById(R.id.tv_nombre_visualizar_potrero)
        val iv_imagen: ImageView = itemView.findViewById(R.id.iv_imagen_vacuna)
        val iv_icono: ImageView = itemView.findViewById(R.id.iv_evento)

    }
}