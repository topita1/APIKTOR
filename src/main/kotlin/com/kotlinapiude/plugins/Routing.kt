package com.kotlinapiude.plugins

import com.kotlinapiude.routes.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
          call.respondText("TEST!")
         //   call.respondRedirect("/static/index.html")
        }

        imageRouting() //para que mi server exponga el router
        geoRouting()  // funcion de geolocalizacion
    }

}

