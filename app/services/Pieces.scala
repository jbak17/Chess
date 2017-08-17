package services

import model.Game

import scala.annotation.tailrec

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

  //assert(colour == 'b' || colour == 'w')

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
  def filterOccupiedByOwnColour(moves: List[Square], game: List[ChessPiece]): List[Square] = {
    //filter out occupied squares
    moves.filterNot(sq => ownPieces(game).contains(sq))
  }

  /*
  @brief Get a list of squares occupied by the opposition
   */
  def oppositionPieces(game: List[ChessPiece]): List[ChessPiece] = game.filterNot(p => p.colour == this.colour)

  /*
  @brief return a list of pieces of same color as player
   */
  def ownPieces(game: List[ChessPiece]): List[ChessPiece] = game.filter(p => p.colour == this.colour)

  /*
  @brief create a list of squares occupied by same player
  @return Squares occupied by current player
   */
  def ownSquares(pieces: List[ChessPiece]): List[Square] = ownPieces(pieces).map(p => p.location)

  /*
  @brief helper to check we're still on the board.
   */
  def inRange(int: Int): Boolean = int <= 8 && int >= 1

  /*
  @brief filter list of squares to remove those outside of board
   */
  def onBoard(squares: List[Square]): List[Square] = squares.filter(p => inRange(p.C) && inRange(p.R))


  /*
  @brief calculate valid squares from a given position branching
  out recursively in all four directions.
   */
  @tailrec
  final def getSquaresFromDirection(direction: (Int, Int), game: Game, squares: List[Square] = List(this.location)): List[Square] = {

    val opposition = oppositionPieces(game.currentBoard)

    val newSquare = Square(squares.head.R + direction._1, squares.head.C + direction._2)

    /* if opposition square, add to list and return.
    if occupied to own color return list
    if not occupied add square to list and continue branch.
     */
    if (!(inRange(newSquare.C) && inRange(newSquare.R))){
      squares}
    else if (opposition.contains(newSquare)){
      newSquare :: squares
    } else if (game.currentBoard.contains(newSquare)){
      squares
    } else {
      getSquaresFromDirection(direction, game, newSquare :: squares)
    }
  }

  /*
  @brief get moves for pieces that move horizontally
   */
  def goHorizontal(game: Game): List[Square] = {

    val directions = List((0,1), (0,-1), (1,0), (-1,0))

    var moves: List[Square] = List()

    for (d <- directions){
      moves ::: getSquaresFromDirection(d, game)
    }
    moves
  }

  /*
  @brief get moves for pieces that move diagonallu
   */
  def goDiagonal(game: Game): List[Square] = {

    val directions = List((1,1), (-1,-1), (-1,1), (1,+1))

    var moves: List[Square] = List()

    for (d <- directions){
      moves ::: getSquaresFromDirection(d, game)
    }
    moves
  }

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
    filterOccupiedByOwnColour(moves, game.currentBoard)

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

    //check potential capture squares are occupied by opposition
    val capSquare = captures.filter(sq => game.currentBoard.contains(sq))
    val capSquareWithOpposition = capSquare.filter(sq => oppositionPieces(game.currentBoard).contains(sq))

    //check all squares are on the board.
    onBoard(capSquare ::: moves)
  }
}

class Rook(color: Char, loc: Square) extends ChessPiece {

  val colour: Char = color
  val location: Square = loc

  def move(newLocation: Square): ChessPiece = new Rook(this.colour, newLocation)
  /*
  @brief calculates a list of valid moves
   */
  def validMove(game: Game): List[Square] = goHorizontal(game)

}

class Knight (color: Char, loc: Square) extends ChessPiece {
  val colour: Char = color
  val location: Square = loc

  def move(newLocation: Square): ChessPiece = new Knight(this.colour, newLocation)

  def validMove(game: Game): List[Square] = {
    //row is up and down; column left and right
    val moves = onBoard(List(
      Square(location.R+2, location.C-1), //up left
      Square(location.R+1, location.C-2), //left up
      Square(location.R-1, location.C-2), //left down
      Square(location.R-2, location.C-1), //down left
      Square(location.R+2, location.C+1), //up right
      Square(location.R+1, location.C+2), //right up
      Square(location.R-1, location.C+2), //right down
      Square(location.R-2, location.C+1), //down right
    ))

    val own: List[Square] = ownSquares(game.currentBoard)
    //make sure we don't clash with our own pieces
    //moves.filter(x => ownPieces(game.currentBoard).contains(x))
    moves.filterNot(own.contains(_))
  }


}

class Bishop (color: Char, loc: Square) extends ChessPiece {
  val colour: Char = color
  val location: Square = loc

  def move(newLocation: Square): ChessPiece = new Bishop(this.colour, newLocation)

  def validMove(game: Game): List[Square] = goDiagonal(game)


}

class Queen (color: Char, loc: Square) extends ChessPiece {
  val colour: Char = color
  val location: Square = loc

  def move(newLocation: Square): ChessPiece = new Queen(this.colour, newLocation)

  def validMove(game: Game): List[Square] = goDiagonal(game) ::: goHorizontal(game)


}



object ChessPiece {

  val board: List[Square] = (for (c <- 1 to 8; r <- 1 to 8) yield Square(c, r)).toList




}

