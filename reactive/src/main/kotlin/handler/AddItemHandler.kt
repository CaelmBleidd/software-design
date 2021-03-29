package handler

import com.mongodb.rx.client.MongoCollection
import io.netty.buffer.ByteBuf
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import io.reactivex.netty.protocol.http.server.HttpServerResponse
import io.reactivex.netty.protocol.http.server.RequestHandler
import model.Item
import rx.Observable
import util.getParameter

data class AddItemHandler(val items: MongoCollection<Item>): RequestHandler<ByteBuf, ByteBuf> {
    override fun handle(
        request: HttpServerRequest<ByteBuf>,
        response: HttpServerResponse<ByteBuf>
    ): Observable<Void> {
        val params = request.queryParameters
        val name = params.getParameter("name", "AddItem: missing 'name' parameter")
        val price = params.getParameter("price", "AddItem: missing 'currency' parameter").toDouble()
        val item = Item(name, price)
        val result = items.insertOne(item).map { item.toString() }
        return response.writeString(result)
    }
}