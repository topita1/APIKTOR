package com.kotlinapiude.models

import kotlinx.serialization.Serializable

@Serializable //para poder pasarlo luego a un Json
data class Imagen (val id:Int, val nombre:String)
{

}