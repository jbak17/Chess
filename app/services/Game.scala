package services

case class Game (
                white: User,
                black: User,
                timeWhite: Int, //@todo make this a time
                timeBlack: Int,
                currentBoard: List[ChessPiece] = List(),
                moveHistory: List[Square] = List()
                )

object Game {

}
