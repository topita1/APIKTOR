package com.kotlinapiude.routes

import com.kotlinapiude.models.Geolocalizacion
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.pow
import kotlin.math.sqrt

private val geos = mutableListOf(
    Geolocalizacion(id = 1, latitud = -34.91681, longitud = -56.15768, nombresede ="Facultad UDE Pocitos", distancia = 0.0),
    Geolocalizacion(id = 2, latitud = -34.90800, longitud = -56.19556, nombresede ="Facultad UDE Centro", distancia = 0.0),
    Geolocalizacion(id = 3, latitud = -34.93964, longitud = -54.93296, nombresede ="Facultad UDE Maldonado", distancia = 0.0),
    //Geolocalizacion(id = 4, latitud = -34.88999, longitud = -56.15532, nombresede ="Mi casa", distancia = 0.0)
)
fun Route.geoRouting() {
    route("/getgeo") {

        post("/buscarsede") {
            val params = call.receiveParameters()
            val Lat = params["latitud"]?.toDoubleOrNull() //latitud que recibo
            val Lng = params["longitud"]?.toDoubleOrNull() // longitud

            if (Lat != null && Lng != null) {
                val masCercana = buscarSedeMasCercana(Lat, Lng)
                //call.respondText("La sede más cercana es ${masCercana.nombresede} a una distancia de ${masCercana.distancia} km.")
                // lo separo con comas, asi del lado del html puedo hacer un split
                call.respondText("${masCercana.nombresede},${masCercana.distancia}, ${masCercana.latitud}, ${masCercana.longitud}")
            } else {
                call.respondText("Coordenadas no válidas", status = HttpStatusCode.BadRequest)
            }
        }
    }

} // cierra funcion

//-------------------------------  Funciones de calculos ----------------------------------------------------------------

private fun buscarSedeMasCercana(Lat: Double, Lng: Double): Geolocalizacion {
    var masCercana: Geolocalizacion? = null
    var minDistancia = Double.MAX_VALUE

    for (sede in geos) {
        val distancia = calcularDistancia(Lat, Lng, sede.latitud, sede.longitud)
        if (distancia < minDistancia) {
            minDistancia = distancia
            masCercana = sede
            masCercana.distancia = distancia
        }
    }

    return masCercana ?: Geolocalizacion(0, 0.0, 0.0, "No hay sedes disponibles", distancia = -1.0)
}

//calcula distancia dadas 2 coordenadas -------------------------------------------------------------

private fun calcularDistancia(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadius = 6371 // Radio de la Tierra en kilómetros
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = Math.sin(dLat / 2).pow(2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(dLon / 2).pow(2)
    val c = 2 * Math.atan2(sqrt(a), sqrt(1 - a))
    return earthRadius * c
}