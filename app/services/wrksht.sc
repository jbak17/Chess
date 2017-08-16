import model.Game
import services.{ChessPiece, Pawn, Square}

val i: Int = 1
val sq: Square = Square(1,1)

val r = List(1,2,3,4,5,6,7,8)

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


val blPawns: List[Pawn] = r.map(p => new Pawn('b', Square(7, p)))
