package handler

import com.mongodb.rx.client.MongoCollection
import io.netty.buffer.ByteBuf
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import io.reactivex.netty.protocol.http.server.HttpServerResponse
import io.reactivex.netty.protocol.http.server.RequestHandler
import model.Currency
import model.User
import rx.Observable
import util.getParameter

data class RegisterHandler(val users: MongoCollection<User>): RequestHandler<ByteBuf, ByteBuf> {
    override fun handle(
        request: HttpServerRequest<ByteBuf>,
        response: HttpServerResponse<ByteBuf>
    ): Observable<Void> {
        val params = request.queryParameters
        val login = params.getParameter("login", "Register: missing 'login' parameter")
        val currency = params.getParameter("currency", "Register: missing 'currency' parameter")
        val user = User(login, Currency.valueOf(currency))
        val result = users.insertOne(user).map { user.toString() }
        return response.writeString(result)
    }

}