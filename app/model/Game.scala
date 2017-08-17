/*
  Includes:

  Definitions:
      Game class for saving

  Methods to:
      save a game instance


 */


package model


import org.joda.time.DateTime
import services._

case class Game (
                  white: UserInstance,
                  black: UserInstance,
                  _id: Long = new java.util.Random().nextLong(),
                  timeWhite: DateTime = new DateTime(),
                  timeBlack: DateTime = new DateTime(),
                  currentBoard: List[ChessPiece] = Game.creatInitialBoard(),
                  moveHistory: List[String] = List(),
                  created: DateTime = DateTime.now()
                )

object Game {

  /*Create a starting board with a full complement of
black and white pieces
 */
  def creatInitialBoard(): List[ChessPiece] = {

    def createPawns(): List[Pawn] = {
      val r = List(1,2,3,4,5,6,7,8)
      val blPawns: List[Pawn] = r.map(p => new Pawn('b', Square(7, p)))
      val whPawns: List[Pawn] = r.map(p => new Pawn('w', Square(2, p)))
      whPawns ::: blPawns
    }
    def createRooks(): List[Rook] = {
      val nums = List(1, 8)

      val wrks = nums.map(x => new Rook('w', Square(1, x)))
      val brks = nums.map(x => new Rook('b', Square(8, x)))

      wrks ::: brks
    }
    def createKnights(): List[Knight] = {
      val nums = List(2, 7)

      val wrks = nums.map(x => new Knight('w', Square(1, x)))
      val brks = nums.map(x => new Knight('b', Square(8, x)))

      wrks ::: brks
    }
    def createBishops(): List[Bishop] = {
      val nums = List(3, 5)

      val wrks = nums.map(x => new Bishop('w', Square(1, x)))
      val brks = nums.map(x => new Bishop('b', Square(8, x)))

      wrks ::: brks
    }
    def createQueens(): List[Queen] = {

      val white = new Queen('w', Square(1, 4))
      val black = new Queen('b', Square(8, 4))

      List(white, black)
    }
    def createKings(): List[King] = {

      val white = new King('w', Square(1, 5))
      val black = new King('b', Square(8, 5))

      List(white, black)
    }

    var pieceList: List[ChessPiece] = List()
    List(createPawns(), createRooks(), createKings(), createBishops(), createQueens(), createKnights()).foldLeft(pieceList)(_ ::: _)

  }


  /*
  Update a game when a new move is made
  @para   gameID: used to get the correct game
  @para   move:
   */
  def recordMove(gameID: Long, move: Int): Unit = ???


}

