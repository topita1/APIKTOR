package com.kotlinapiude.models
import kotlinx.serialization.Serializable

@Serializable //para poder pasarlo luego a un Json

class Geolocalizacion (val id:Int, val latitud:Double, val longitud:Double, val nombresede:String, var distancia:Double){


}

