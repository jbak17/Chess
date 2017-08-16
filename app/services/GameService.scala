package services

import javax.inject.{Inject, Singleton}

import model.{Game, UserInstance}

/*
Service for interacting with saved instances of games, and
turning game information into JSON.

 */

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
