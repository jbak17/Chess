import org.scalatestplus.play._

import scala.collection.mutable
import model.{Game, UserInstance}
import services._

class ChessSpec extends PlaySpec {
/*
  "A Stack" must {
    "pop values in last-in-first-out order" in {
      val stack = new mutable.Stack[Int]
      stack.push(1)
      stack.push(2)
      stack.pop() mustBe 2
      stack.pop() mustBe 1
    }
    "throw NoSuchElementException if an empty stack is popped" in {
      val emptyStack = new mutable.Stack[Int]
      a [NoSuchElementException] must be thrownBy {
        emptyStack.pop()
      }
    }
  }*/

  val u1: UserInstance = UserInstance("bob", "Brown")
  val u2: UserInstance = UserInstance("bob", "Green")
  val g = new Game(u1, u2)

  "An initial game" must {
    "have 32 pieces" in {
      g.currentBoard.length mustBe 32
    }
  }

  "An initial game" must {
    "have 4 bishops" in {
      g.currentBoard.count(p => p.isInstanceOf[Bishop]) mustBe 4
    }
  }

  "An initial game" must {
    "have 4 rooks" in {
      g.currentBoard.count(p => p.isInstanceOf[Rook]) mustBe 4
    }
  }

  "An initial game" must {
    "have 4 knights" in {
      g.currentBoard.count(p => p.isInstanceOf[Knight]) mustBe 4
    }
  }

  "An initial game" must {
    "have 16 pawns" in {
      g.currentBoard.count(p => p.isInstanceOf[Pawn]) mustBe 16
    }
  }
  "An initial game" must {
    "have 2 queens" in {
      g.currentBoard.count(p => p.isInstanceOf[Queen]) mustBe 2
    }
  }
  "An initial game" must {
    "have 2 kings" in {
      g.currentBoard.count(p => p.isInstanceOf[King]) mustBe 2
    }
  }

  "A rook should have no moves at the start of a game in" in {
      val r: ChessPiece = g.currentBoard.filter(p => p.isInstanceOf[Rook]).head
      r.validMove(g).isEmpty mustBe true
  }

  "A knight should have two valid moves at the start of a game in" in {
    val r: ChessPiece = g.currentBoard.filter(p => p.isInstanceOf[Knight]).head
    //Console.println(r.validMove(g).toString)
    r.validMove(g).length mustBe 2
  }

  "A bishop should have no valid moves at the start of a game in" in {
    val r: ChessPiece = g.currentBoard.filter(p => p.isInstanceOf[Bishop]).head
    //Console.println(r.validMove(g).toString)
    r.validMove(g).length mustBe 0
  }

  "A pawn should have two valid moves at the start of a game in" in {
    val r: ChessPiece = g.currentBoard.filter(p => p.isInstanceOf[Pawn]).head
    val m: List[Square] = r.validMove(g)
    r.validMove(g).length mustBe 2
  }



}