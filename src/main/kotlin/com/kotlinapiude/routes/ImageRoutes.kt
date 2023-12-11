package com.kotlinapiude.routes

import com.kotlinapiude.models.Imagen
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val images = mutableListOf(
    Imagen(id = 1, nombre = "panelinferior.png"),
    Imagen(id = 2, nombre = "videoUDE.mp4"),
    Imagen(id = 3, nombre = "salon2.png"),
    Imagen(id = 4, nombre = "salon1.png")
)
fun Route.imageRouting() {
    route("/image") {
        get {
            // Si el listado no es vacío, lo devuelvo
            if (images.isNotEmpty()) {
                call.respond(images) //al ser serializable el listado, puedo pasarlo a un JSON directamente
            } else {

                call.respondText("No hay imágenes ingresadas", status = HttpStatusCode.OK)
            }
         }
        get("{id?}"){
            val id= call.parameters["id"] ?: return@get call.respondText ( "ID no encontrado",
                status = HttpStatusCode.BadRequest )

            // si existe el idintento buscarlo,
            // y en caso que no encuentre una img con ese id respondo que no la encontro

            val nombre = images.find { it.id == id.toInt() } ?: return@get call.respondText ( "Imagen con ID $id NO encontrado",
                status = HttpStatusCode.NotFound )
            call.respond(nombre)
        }

    }
}


