package services

import controllers.UserController


case class User (
                name: String,
                email: String,
                games: List[Game] = List(), //@todo change this to 'game'
                record: (Int, Int, Int) = (0,0,0) //win - loss - draw
                )

case class NU(name: String, email: String)



object User {

  var users: Set[User] = Set(
    User("Arnold", "arnold@chess.com"),
    User("Samantha", "sam@chess.com"),
    User("Juno", "juno@chess.com"),
    User("Rose", "rose@chess.com")
  )

  /*
  @brief Create a new user
   */
  def createUser(user: User): Unit = {

    users = users + user
  }



  /*
  @brief return list of current users
   */
  def listUsers(): List[User] = {
    users.toList.sortBy(_.name)
  }

  /*
  @brief get user based on email address.
  Note that this might return nothing if the email address
  is not in the system!
   */
  def getUserByEmail(email: String): List[User] = {
    users.filter(_.email == email).toList
  }



}
