package services

import com.google.inject.Singleton
import org.mongodb.scala.{MongoClient, MongoDatabase}

@Singleton
class MongoConnect
{
  final private val mongoClient = MongoClient ( "mongodb://localhost:27017/" )
  final val mongod: MongoDatabase = mongoClient.getDatabase ( "chess" )
}
