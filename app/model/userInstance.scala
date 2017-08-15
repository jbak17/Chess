package model

import services.Game
import java.util.UUID

import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.collection.Document
import org.mongodb.scala.bson.ObjectId

//imports to convert from case class to document
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromRegistries, fromProviders}

object UserInstance {
  def apply(name: String, email: String): UserInstance = UserInstance(new ObjectId(), name, email, games = List())

}

case class UserInstance(
                  _id: ObjectId = new ObjectId(),
                  name: String,
                  email: String,
                  games: List[Game] = List(),
                  //record: (Int, Int, Int) = (0,0,0) //win - loss - draw
                ){
  override def toString: String = {
    s"$name:   "
  }

}


case class NU(name: String, email: String)

