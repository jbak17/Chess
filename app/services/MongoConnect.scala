package services

import com.google.inject.Singleton
import javax.inject.Singleton

import model.{Game, UserInstance, testGame}
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase, Observable, Observer}
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

import scala.language.postfixOps

class MongoConnect
{
  final private val mongoClient = MongoClient ( "mongodb://localhost:27017/" )

  val userCodecRegistry = fromRegistries(fromProviders(classOf[UserInstance]), DEFAULT_CODEC_REGISTRY )

  final val userdb: MongoDatabase = mongoClient.getDatabase ( "chess" ).withCodecRegistry(userCodecRegistry)
  final val chessdb: MongoDatabase = mongoClient.getDatabase ( "games" )

  val gameCollection: MongoCollection[Document] = chessdb.getCollection("gameInstances")

}
