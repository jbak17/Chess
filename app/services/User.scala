package services


case class User (
                name: String,
                email: String,
                games: List[Game] = List(), //@todo change this to 'game'
                record: (Int, Int, Int) = (0,0,0) //win - loss - draw
                )
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
  def createUser(name: String, email: String) = {

    val user = User(name, email)

    users = users + user
  }

  /*
  @brief return list of current users
   */
  def listUsers() = {
    users.toList.sortBy(_.name)
  }

  /*
  @brief get user based on email address.
  Note that this might return nothing if the email address
  is not in the system!
   */
  def getUserByEmail(email: String): User = {
    users.filter(_.email == email).head
  }



}
