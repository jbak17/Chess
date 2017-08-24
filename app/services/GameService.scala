package services

import javax.inject.{Inject, Singleton}

import model.{Game, UserInstance}
import org.mongodb.scala._
import com.mongodb.async.client.{Observer, Subscription}
import org.mongodb.scala.{Completed, Document}

import scala.concurrent.Await
import scala.language.postfixOps
import scala.concurrent.duration._


/*
Service for interacting with saved instances of games, and
turning game information into JSON.

 */

@Singleton
class GameService @Inject () (mongoConnect: MongoConnect) {

  var gamesList: Seq[Game] = Seq()
  //sample data for testing functions
  var savedGames: Set[Game]= Set(
    Game("juno@chess.com", "arnold@chess.com"),
    Game("sam@chess.com","juno@chess.com"),
    Game("rose@chess.com","arnold@chess.com"),
    Game("sam@chess.com","rose@chess.com")
  )


    /*
  @brief Save a new game instance
   */
  def saveGame(game: Document): Unit = {

    mongoConnect.gameCollection.insertOne(game).subscribe((C: Completed) => println("new game inserted"))
  }


/*
  def loadGame(): Unit = {
    val observable = mongoConnect.gameCollection.find()

    observable.subscribe( observer = new Observer[Document] {
      override def onError(e: Throwable): Unit = {
        println ( "Game database error: " + e.getMessage )
      }
      override def onComplete(): Unit = {
        println ("Game found")
      }
      def onSubscribe(subscription: Subscription): Unit = ???
      def onNext(result: Game): Unit = ???

    })

    val results: Seq[Document] = Await.result ( observable.toFuture(), 3 seconds )

    //results.foreach(gamesList = gamesList + results)
  }
  */


/*
  @brief List all games sorted by the order in which they were created.
   */
  def list(): List[Game] = {

    savedGames.toList.sortBy(_.created.getMillis)
/*
    val observable = collection.find()

    observable.subscribe(new Observer[Game](){

      var batchSize: Long = 10
      var seen: Long = 0
      var subscription: Option[Subscription] = None

      override def onSubscribe(subscription: Subscription): Unit = {
        this.subscription = Some(subscription)
        subscription.request(batchSize)
      }

      override def onNext(result: Game): Unit = {
        Console.println(Game.toString)

        gameList += result
        seen += 1
        if ( seen == batchSize )
        {
          seen = 0
          subscription.get.request ( batchSize )
        }


        seen += 1
        if (seen == batchSize) {
          seen = 0
          subscription.get.request(batchSize)
        }
      }

      override def onError(e: Throwable): Unit = println(s"Error: $e")

      override def onComplete(): Unit = println("Completed")
    })

    Await.result ( observable.toFuture(), 3 seconds ).toList

    //gameList.appendAll(results)

*/
  }

  def showGameByUser(user: UserInstance): List[Game] = {

    savedGames.toList.filter( g => g.black == user.email || g.white == user.email)

  }

}
