/*
  Includes:

  Definitions:
      Game class for saving

  Methods to:
      save a game instance


 */


package services


import javax.inject.{Inject, Singleton}

import model.UserInstance
import org.joda.time.DateTime

case class Game (
                  white: UserInstance,
                  black: UserInstance,
                  _id: Long = new java.util.Random().nextLong(),
                  timeWhite: DateTime = new DateTime(),
                  timeBlack: DateTime = new DateTime(),
                  currentBoard: List[ChessPiece] = List(),
                  moveHistory: List[Square] = List(),
                  created: DateTime = DateTime.now()
                )
@Singleton
class GameService @Inject () (userService: UserService) {

  //sample data for testing functions
  var savedGames: Set[Game]= Set(
    Game(userService.listUsers.head, userService.listUsers.tail.head),
    Game(userService.listUsers.head, userService.listUsers.tail.head),
    Game(userService.listUsers.head, userService.listUsers.tail.head),
    Game(userService.listUsers.head, userService.listUsers.tail.head),
    )

  /*
@brief Create a new user
 */
  def saveGame(game: Game): Unit = {

    savedGames = savedGames + game

  }

  /*
  @brief List all games sorted by the order in which they were created.
   */
  def list(): List[Game] = {

    savedGames.toList.sortBy(_.created.getMillis)

  }

  def showGameByUser(user: UserInstance): List[Game] = {

    savedGames.toList.filter( g => g.black == user || g.white == user)

  }

}
