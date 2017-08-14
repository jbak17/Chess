package services

/*
@brief represent locations on a chess board, eg A1, F6
 */
case class Square(R: Int,C: Int){

  val ColumnMap: Map[Int, Char] = Map(1->'A', 2->'B', 3->'C', 4->'D', 5->'E', 6->'F', 7->'G', 8->'H')

  override def toString: String = ColumnMap(C) + R.toString

}

trait ChessPiece {

  val colour: Char
  val captured: Boolean
  val location: Square

  /*
  @brief returns updated piece at newLocation
   */
  def move(newLocation: Square): ChessPiece

  /*
  @brief calculates a list of valid moves
   */
  def validMove(game: Game): List[Square]

  }




class King(color: Char, loc: Square) extends ChessPiece {

  val inCheck: Boolean = false
  val captured: Boolean = false
  val colour: Char = color
  val location: Square = loc

  def move(newLocation: Square): ChessPiece = new King(this.colour, newLocation)

  def validMove(game: Game): List[Square] = {

    //list all possible moves
    val moves = List(
      //row is up and down; column left and right
      Square(location.R, location.C - 1), //left
      Square(location.R, location.C + 1), //right
      Square(location.R + 1, location.C), //up
      Square(location.R - 1, location.C), //down
      Square(location.R - 1, location.C - 1), //left and up
      Square(location.R - 1, location.C - 1), // left and down
      Square(location.R + 1, location.C + 1), // right and up
      Square(location.R + 1, location.C + 1) // right and down
    )

    //filter out occupied squares
    val notOccupied: List[Square] = moves.diff(game.currentBoard)
    //filter out squares in check
    moves

  }
}


object ChessPiece {

  val board: List[Square] = (for (c <- 1 to 8; r <- 1 to 8) yield Square(c, r)).toList


}

