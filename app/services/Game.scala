/*
  Includes:

  Definitions:
      Game class for saving

  Methods to:
      save a game instance


 */


package services

import org.joda.time.DateTime

case class Game (
                white: User,
                black: User,
                timeWhite: DateTime = new DateTime(),
                timeBlack: DateTime = new DateTime(),
                currentBoard: List[ChessPiece] = List(),
                moveHistory: List[Square] = List(),
                created: DateTime = DateTime.now()
                )

object Game {

  //sample data for testing functions
  var savedGames: Set[Game]= Set(
    Game(User.listUsers.head, User.listUsers.tail.head),
    Game(User.listUsers.head, User.listUsers.tail.head),
    Game(User.listUsers.head, User.listUsers.tail.head),
    Game(User.listUsers.head, User.listUsers.tail.head),
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

  def showGameByUser(user: User): List[Game] = {

    savedGames.toList.filter( g => g.black == user || g.white == user)

  }

}
