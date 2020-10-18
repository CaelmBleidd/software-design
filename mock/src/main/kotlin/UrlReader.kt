import java.net.URL

fun String.readAsTest(): String {
    val url = toUrl()
    val reader = url.openStream().bufferedReader()
    return buildString {
        reader.lines().forEach {
            appendLine(it)
        }
    }
}

fun String.toUrl() = URL(this)