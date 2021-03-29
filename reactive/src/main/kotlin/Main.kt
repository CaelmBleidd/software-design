import db.createRxDatabase
import handler.AddItemHandler
import handler.RegisterHandler
import handler.ViewItemsHandler
import model.Item
import model.User
import server.createRxServer

fun main() {
    val database = createRxDatabase()
    val items = database.getCollection("items", Item::class.java)
    val users = database.getCollection("users", User::class.java)
    val handlers = mapOf(
        "/add" to AddItemHandler(items),
        "/view" to ViewItemsHandler(users, items),
        "/register" to RegisterHandler(users)
    )
    createRxServer(handlers).awaitShutdown()
}