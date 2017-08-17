/*
  Includes:

  Definitions:
      Game class for saving

  Methods to:
      save a game instance


 */


package model


import org.joda.time.DateTime
import org.mongodb.scala.bson.collection.immutable.Document
import services._

case class testGame(white: String, black: String)

case class Game (
                  white: String,//use email addresses to represent users
                  black: String,
                  timeWhite: Long,
                  timeBlack: Long,
                  currentBoard: List[ChessPiece] = Game.creatInitialBoard(),
                  moveHistory: List[String] = List(),
                  created: DateTime = DateTime.now()
                ){
  override def toString: String = s"Time remaining white: ${this.timeWhite}\n" +
    s"Time remaining black: ${this.timeWhite}\n" +
    s"Current board: ${currentBoard.toString()}"

  def toBson(piece: ChessPiece): Document ={
    Document("colour"->piece.colour.toString,
      "col"->piece.location.C,
      "row"->piece.location.R,
      "type"->piece.kind,
      "btime" -> timeBlack,
      "wtime" -> timeWhite)
  }

  def currentBoardtoDocument: Document = {
    val pieces: List[Document] = currentBoard.map(p => toBson(p))
    Document("pieces"->pieces)
  }

    /*
    for each pieces on the board, we want document format string->value
     */

}

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

