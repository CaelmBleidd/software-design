import com.google.gson.JsonObject
import com.google.gson.JsonParser

fun String.parseResponse(): List<VkInfo> {
    val jsonResponse = JsonParser().parse(this).asJsonObject
    val jsonEntries = jsonResponse.getAsJsonObject("response").getAsJsonArray("items")
    return jsonEntries.map {
        it as JsonObject
        VkInfo(
            it.get("id").asLong,
            it.get("owner_id").asLong,
            it.get("date").asLong
        )
    }
}