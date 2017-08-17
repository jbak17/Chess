package services

import javax.inject.{Inject, Singleton}

import model.UserInstance
import org.mongodb.scala.{Completed, Document, MongoCollection, Observer}
import org.mongodb.scala.model.Filters._

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.collection.mutable.ListBuffer
import scala.concurrent.Await

@Singleton
class UserService  @Inject () (mongoConnect: MongoConnect) {

  val userCollection: MongoCollection[UserInstance] = mongoConnect.userdb.getCollection("users")

  val userList = ListBuffer.empty [ UserInstance ]


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

    val newUser: Document = Document("name"->user.name, "email"->user.email)

    userCollection.insertOne(user).subscribe((C: Completed) => println("new person inserted"))

  }

  /*
  @brief return list of current users
   */
  def listUsers(): List[UserInstance] = {
    val observable = userCollection.find()

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

    val observable = userCollection.find(equal("email", email)).first()

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

    val observable = userCollection.find()

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

}
