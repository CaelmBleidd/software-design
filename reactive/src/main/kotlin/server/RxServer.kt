package server

import io.netty.buffer.ByteBuf
import io.reactivex.netty.protocol.http.server.HttpServer
import io.reactivex.netty.protocol.http.server.RequestHandler

fun createRxServer(handlers: Map<String, RequestHandler<ByteBuf, ByteBuf>>): HttpServer<ByteBuf, ByteBuf> = HttpServer
    .newServer(8080)
    .start() { req, resp ->
        handlers.getValue(req.uri.split("\\?").first()).handle(req, resp)
    }
