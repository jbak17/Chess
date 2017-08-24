/*
  Includes: This class will contain the logic for
   keeping track of and representing the game. The logic
   for the game itself is in GameService.


  Definitions:
      Game class for saving

  Methods to:
      save a game instance as a Document
      create a new board at the start of the game


 */

package model

import org.joda.time.DateTime
import org.mongodb.scala.bson.{BsonDocument, BsonValue}
import org.mongodb.scala.bson.collection.immutable.Document
import services._

case class Game (
                  white: String,//use email addresses to represent users
                  black: String,
                  timeWhite: Long = 0,
                  timeBlack: Long = 0,
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
      "type"->piece.kind)
  }

  /*
  @brief return an array representation of the board. Each piece is a Document.
   */
  def currentBoardtoArray(board: List[ChessPiece]): Array[Document] = board.map(p => toBson(p)).toArray



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
    //remove the old square from the game

    //add the relevant move history

    //create the new game instance with the updated piece list

  def gameToDocument(game: Game): Document = {
    val docs: Array[Document] = game.currentBoardtoArray(game.currentBoard)
    Document("white"->game.white,
      "black"->game.black,
      "board"->docs.toSeq
    )
  }


  /*
  def documentToGame(document: Document): Game = {
    val white: String = document.getString("white")
    val black: String = document.getString("black")
    val piecesBSON: List[BsonValue] = document.get("board").toList
    val piecesDoc: List[BsonDocument] = piecesBSON.map(bson => bson.asDocument())
    val pieces: List[ChessPiece] = piecesDoc.map( d => new Piece(
      d.get("colour").toString)


     //get pieces
  }*/


}

