package interfaces

import uv.tc.cowtrol.poko.Evento

interface ListenerRecycleEvento {

    fun clickAgregarEvento(evento: Evento,posicion:Int)
}