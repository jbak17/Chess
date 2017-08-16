package model

import Game

import org.mongodb.scala.bson.ObjectId

object UserInstance {
  def apply(name: String, email: String): UserInstance = UserInstance(new ObjectId(), name, email, games = List())

}

case class UserInstance(
                  _id: ObjectId = new ObjectId(),
                  name: String,
                  email: String,
                  games: List[Long] = List() //list of gameIDs
                ){
  override def toString: String = {
    s"$name:   "
  }

}


case class NU(name: String, email: String)

