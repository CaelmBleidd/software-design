package investor.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.http.HttpClient
import java.net.http.HttpResponse
import lombok.SneakyThrows
import investor.model.Error

@SneakyThrows
fun checkResponseCode(response: HttpResponse<String>) {
    if (response.statusCode() == 200) return
    val error = objectMapper.readValue(response.body(), Error::class.java)
    throw RuntimeException("Error: ${error.error}, message: ${error.message}, code: ${error.status}")
}

val objectMapper: ObjectMapper = ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

val httpClient: HttpClient = HttpClient.newHttpClient()