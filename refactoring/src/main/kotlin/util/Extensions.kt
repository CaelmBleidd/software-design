package ru.akirakozov.sd.refactoring.util

import java.sql.Connection
import java.sql.Statement
import javax.servlet.http.HttpServletResponse

fun <T> Connection.use(block: (Connection) -> T): T {
    try {
        return block(this)
    } finally {
        this.close()
    }
}

fun <T> Statement.use(block: (Statement) -> T): T {
    try {
        return block(this)
    } finally {
        this.close()
    }
}

fun HttpServletResponse.createEmpty() {
    setStatusOK()
    writer.println("OK")
}

fun <T> HttpServletResponse.createForCollection(collection: Collection<T>) {
    writer.println("<html><body>")
    collection.forEach { writer.println("$it</br>") }
    writer.println("</body></html>")
    setStatusOK()
}

fun <T> HttpServletResponse.createForSingle(elem: T, msg: String? = null) {
    writer.println("<html><body>")
    msg?.let { writer.println(it) }
    when (elem) {
        is Int -> writer.println(elem)
        else -> writer.println("$elem</br>")
    }
    writer.println("</body></html>")
    setStatusOK()
}

private fun HttpServletResponse.setStatusOK() {
    contentType = "text/html"
    status = HttpServletResponse.SC_OK
}