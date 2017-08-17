package services

import javax.inject.{Inject, Singleton}

import model.{Game, UserInstance}

import org.mongodb.scala._

import com.mongodb.async.client.{Observer, Subscription}
import org.mongodb.scala.{Completed, Document, MongoCollection}

import scala.language.postfixOps


/*
Service for interacting with saved instances of games, and
turning game information into JSON.

 */

@Singleton
class GameService @Inject () (mongoConnect: MongoConnect) {

  //sample data for testing functions
  var savedGames: Set[Game]= Set(
    Game(UserInstance("Juno", "juno@chess.com"), UserInstance("Arnold", "arnold@chess.com")),
    Game(UserInstance("Samantha", "sam@chess.com"), UserInstance("Juno", "juno@chess.com")),
    Game(UserInstance("Rose", "rose@chess.com"), UserInstance("Arnold", "arnold@chess.com")),
    Game(UserInstance("Samantha", "sam@chess.com"), UserInstance("Rose", "rose@chess.com")),
  )


    /*
  @brief Save a new game instance
   */
  def saveGame(game: Game): Unit = {

    //savedGames = savedGames + game
    mongoConnect.gameCollection.insertOne(game).subscribe((C: Completed) => println("new game inserted"))

/*
    val observable: Observable[Completed] = mongoConnect.gameCollection.insertOne(game)

    // Explictly subscribe to actually insert the name:
    observable.subscribe(new Observer[Completed] {

      override def onNext(result: Completed): Unit = println("New Game Inserted")

      override def onSubscribe ( subscription: Subscription ): Unit = {}

      override def onError(e: Throwable): Unit = println("Failed to insert game: " + e.getLocalizedMessage)

      override def onComplete(): Unit = println("Game insert successful")
    })
    */
  }

  val inObs: Observable[Completed] = mongoConnect.gameCollection.insertMany(savedGames.toSeq)

  // Explictly subscribe to actually insert the name:
  inObs.subscribe(new Observer[Completed] {

    override def onNext(result: Completed): Unit = println("New Game Inserted")

    override def onSubscribe ( subscription: Subscription ): Unit = {}

    override def onError(e: Throwable): Unit = println("Failed to insert game: " + e.getLocalizedMessage)

    override def onComplete(): Unit = println("Game insert successful")
  })

/*
  def loadGame(): Unit = {
    val observable = mongoConnect.gameCollection.find()

    observable.subscribe( observer = new Observer[Game] {
      override def onError(e: Throwable): Unit = {
        println ( "Game database error: " + e.getMessage )
      }
      override def onComplete(): Unit = {
        println ("Game found")
      }
      override def onNext(result: Game): Unit = {
        //todo make a default function for reuse
      }
    })

    val results: Seq[Game] = Await.result ( observable.toFuture(), 3 seconds )

    results.foreach(gameList.append(_))
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

    savedGames.toList.filter( g => g.black == user || g.white == user)

  }

}
