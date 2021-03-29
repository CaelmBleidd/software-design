package handler

import com.mongodb.client.model.Filters.eq
import com.mongodb.rx.client.MongoCollection
import io.netty.buffer.ByteBuf
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import io.reactivex.netty.protocol.http.server.HttpServerResponse
import io.reactivex.netty.protocol.http.server.RequestHandler
import model.Item
import model.User
import rx.Observable
import util.convert
import util.getParameter

data class ViewItemsHandler(
    val users: MongoCollection<User>,
    val items: MongoCollection<Item>
) : RequestHandler<ByteBuf, ByteBuf> {
    override fun handle(
        request: HttpServerRequest<ByteBuf>,
        response: HttpServerResponse<ByteBuf>
    ): Observable<Void> {
        val params = request.queryParameters
        val login = params.getParameter("login", "ViewItems: missing 'login' parameter")
        val result = users
            .find(eq("login", login))
            .toObservable()
            .map(User::currency)
            .flatMap { currency ->
                items
                    .find()
                    .toObservable()
                    .map { item -> Item(item.name, convert(currency, item.price)).toString() }
            }
        return response.writeString(result)
    }

}