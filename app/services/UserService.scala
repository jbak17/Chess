package services

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import model.UserInstance
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, MongoDatabase, Observer}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps


import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration


@Singleton
class UserService  @Inject () (mongoConnect: MongoConnect) {

  val userList = ListBuffer.empty [ UserInstance ]

  val userCodecRegistry = fromRegistries(fromProviders(classOf[UserInstance]), DEFAULT_CODEC_REGISTRY )

  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("chess").withCodecRegistry(userCodecRegistry)
  val collection: MongoCollection[UserInstance] = database.getCollection("users")

  /*
  var users: Set[UserInstance] = Set(
    UserInstance("Arnold", "arnold@chess.com"),
    UserInstance("Samantha", "sam@chess.com"),
    UserInstance("Juno", "juno@chess.com"),
    UserInstance("Rose", "rose@chess.com")
  )
  */

  /*
  @brief Create a new user
   */
  def saveUser(user: UserInstance): Unit = {

    collection.insertOne(user).subscribe((C: Completed) => println("new person inserted"))

  }

  /*
  @brief return list of current users
   */
  def listUsers(): List[UserInstance] = {
    val observable = collection.find()

    observable.subscribe( observer = new Observer[UserInstance] {
      override def onError(e: Throwable): Unit = {
        println ( "User database error: " + e.getMessage )
      }
      override def onComplete(): Unit = {
        println ("User found")
      }
      override def onNext(result: UserInstance): Unit = {

      }
    })

    val results: Seq[UserInstance] = Await.result ( observable.toFuture(), 3 seconds )

    results.toList

  }

  /*
  @brief get user based on email address.
  Note that this might return nothing if the email address
  is not in the system!
   */
  def getUserByEmail(email: String): UserInstance = {

    val observable = collection.find(equal("email", email)).first()

    observable.subscribe( observer = new Observer[UserInstance] {
      override def onError(e: Throwable): Unit = {
        println ( "User database error: " + e.getMessage )
      }
      override def onComplete(): Unit = {
        println ("User found")
      }
      override def onNext(result: UserInstance): Unit = {

      }
    })

    Await.result ( observable.toFuture(), 3 seconds )

  }

  def userAlreadyExists(email: String): Boolean = {
    val user: UserInstance = this.getUserByEmail(email)

    if (user.isInstanceOf[UserInstance]) true else false
  }

  def loadUsers(): Unit = {

    val observable = collection.find()

    observable.subscribe( observer = new Observer[UserInstance] {
      override def onError(e: Throwable): Unit = {
        println ( "User database error: " + e.getMessage )
      }
      override def onComplete(): Unit = {
        println ("User found")
      }
      override def onNext(result: UserInstance): Unit = {

      }
    })

    val results: Seq[UserInstance] = Await.result ( observable.toFuture(), 3 seconds )

    results.foreach(userList.append(_))

  }

  loadUsers()
  print(userList)
}
