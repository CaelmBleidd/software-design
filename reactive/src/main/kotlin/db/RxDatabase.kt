package db

import com.mongodb.rx.client.MongoClients
import com.mongodb.rx.client.MongoClients.getDefaultCodecRegistry
import com.mongodb.rx.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider

fun createRxDatabase(): MongoDatabase {
    val pojoCodecRegistry = fromRegistries(
        getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build())
    )

    return MongoClients
        .create("mongodb://localhost")
        .getDatabase("shop")
        .withCodecRegistry(pojoCodecRegistry)
}