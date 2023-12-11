package com.kotlinapiude


import com.kotlinapiude.plugins.configureHTTP
import com.kotlinapiude.plugins.configureMonitoring
import com.kotlinapiude.plugins.configureRouting
import com.kotlinapiude.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.CORS
//import io.ktor.server.application.*

import io.ktor.server.engine.embeddedServer
import io.ktor.server.tomcat.*

//Configuracion del servidor
fun main() {
    embeddedServer(Tomcat, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS){

        allowCredentials = true
       // allowNonSimpleContentTypes = true
        anyHost()
    }
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()

}








