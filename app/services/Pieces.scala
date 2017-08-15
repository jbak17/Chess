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
  val location: Square

  assert(colour == 'b' || colour == 'w')

  /*
  @brief returns updated piece at newLocation
   */
  def move(newLocation: Square): ChessPiece

  /*
  @brief calculates a list of valid moves
   */
  def validMove(game: Game): List[Square]

  /*
  @brief filters the possible moves to remove the squares
  occupied by own side.
  @param moves list of valid moves for this piece
  @param game current list of pieces on board
  @return list of moves this pieces can make
   */
  def filterOccupied(moves: List[Square], game: List[ChessPiece]): List[Square] = {
    //filter out occupied squares
    moves.diff(ownPieces(game))
  }

  /*
  @brief Get a list of squares occupied by the opposition
   */
  def oppositionPieces(game: List[ChessPiece]): List[ChessPiece] = game.filterNot(p => p.colour == this.colour)

  /*
  @brief
   */
  def ownPieces(game: List[ChessPiece]): List[ChessPiece] = game.filter(p => p.colour == this.colour)

}

class King(color: Char, loc: Square) extends ChessPiece {

  val inCheck: Boolean = false
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

    //filter out squares in check
    filterOccupied(moves, game.currentBoard)

  }
}

class Pawn(color: Char, loc: Square) extends ChessPiece {

  val colour: Char = color
  val location: Square = loc
  val advance: Int = if (colour == 'w') 1 else -1

  def move(newLocation: Square): ChessPiece = new Pawn(this.colour, newLocation)

  def validMove(game: Game): List[Square] = {
    //list all possible moves
    var moves = List(
      //row is up and down; column left and right
      Square(location.R + advance, location.C) //up
    )
    //if pawn hasn't moved from starting position
    if(colour =='w' && location.R == 2){
      moves = Square(location.R + 2, location.C) :: moves
    } else if (colour =='b' && location.R == 7) {
      moves = Square(location.R - 2, location.C) :: moves
    }
    //test for capture opportunities
    val captures = List(
      Square(location.R + advance, location.C - 1),
      Square(location.R + advance, location.C + 1))

    //filter out squares in check
    val capSquare = captures diff filterOccupied(moves, game.currentBoard)

    capSquare ::: moves
  }
}

class Rook(color: Char, loc: Square) extends ChessPiece {

  val colour: Char = color
  val location: Square = loc

  def move(newLocation: Square): ChessPiece = new Rook(this.colour, newLocation)
  /*
  @brief calculates a list of valid moves
   */
  def validMove(game: Game): List[Square] = ???

}

class Knight (color: Char, loc: Square) extends ChessPiece {
  val colour: Char = color
  val location: Square = loc

  def move(newLocation: Square): ChessPiece = new Knight(this.colour, newLocation)

  def validMove(game: Game): List[Square] = {
    //row is up and down; column left and right
    val moves = List(
      Square(location.R+2, location.C-1), //up left
      Square(location.R+1, location.C-2), //left up
      Square(location.R-1, location.C-2), //left down
      Square(location.R-2, location.C-1), //down left
      Square(location.R+2, location.C+1), //up right
      Square(location.R+1, location.C+2), //right up
      Square(location.R-1, location.C+2), //right down
      Square(location.R-2, location.C+1), //down right
    )

    //make sure we don't clash with our own pieces
    moves diff ownPieces(game.currentBoard)
  }


}

class Bishop (color: Char, loc: Square) extends ChessPiece {
  val colour: Char = color
  val location: Square = loc

  def move(newLocation: Square): ChessPiece = new Bishop(this.colour, newLocation)

  def validMove(game: Game): List[Square] = ???

}

class Queen (color: Char, loc: Square) extends ChessPiece {
  val colour: Char = color
  val location: Square = loc

  def move(newLocation: Square): ChessPiece = new Queen(this.colour, newLocation)

  def validMove(game: Game): List[Square] = ???


}



object ChessPiece {

  val board: List[Square] = (for (c <- 1 to 8; r <- 1 to 8) yield Square(c, r)).toList


}

