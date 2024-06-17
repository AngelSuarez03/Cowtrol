package adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uv.tc.cowtrol.R

class AdaptadorVeterinario(val onClickListener:(String)->Unit ):RecyclerView.Adapter<AdaptadorVeterinario.ViewHolder>() {

    val nombre= arrayOf("Alberto Martínez Álvarez","Raúl Cortinez Vázquez","Beto Albarado")
    val telefono = arrayOf(2284413843,2831462214,4789641236)
    val imagen = intArrayOf(R.drawable.veterinario, R.drawable.veterinario, R.drawable.veterinario)
    val icono = intArrayOf(R.drawable.phone, R.drawable.phone, R.drawable.phone)


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_recycle_veterinarios,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nombre.text=nombre[position]
        viewHolder.telefono.text= telefono[position].toString()
        viewHolder.imagen.setImageResource(imagen[position])
        viewHolder.icono.setImageResource(icono[position])
        viewHolder.icono.setOnClickListener {
            if (position==0){
                onClickListener(2284413843.toString())
            }
            if (position==1){
                onClickListener(2831462214.toString())
            }
            if (position==2){
                onClickListener(4789641236.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return nombre.size
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre: TextView=itemView.findViewById(R.id.tv_nombre_veterinario)
        var telefono: TextView=itemView.findViewById(R.id.tv_telefono_veterinario)
        var imagen:ImageView=itemView.findViewById(R.id.iv_imagen_veterinario)
        var icono:ImageButton=itemView.findViewById(R.id.ib_llamada)
        }
    }