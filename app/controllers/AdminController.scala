/*
  Controller for:
   the creation of new game instances,
   selection of game options

  Methods:
    list - show all games
    save - add to persistence
    create - render form to select game options



 */

package controllers

import javax.inject.{Inject, Singleton}

import model.Game
import org.mongodb.scala.bson.collection.immutable.Document
import services.GameService
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{Messages, MessagesApi}
import play.api.mvc.{AbstractController, ControllerComponents, Flash}
import play.twirl.api.Html

case class gameData(player1: String, player2: String, startTime: Int, increment: Int)

@Singleton
class AdminController @Inject()(cc: ControllerComponents, messagesApi: MessagesApi, Game: GameService) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index = Action { implicit request =>
    val input: Html =  Html.apply("<h1>Welcome</h1>")
    Ok(views.html.landing(input))
  }

  def login = Action { implicit request =>

    Ok(views.html.Users.login(null))
  }


  def list = Action { implicit request =>
    val games = Game.list
    Ok(views.html.gameIndex(games))
  }



  def save = Action { implicit request =>
    val newGame: Form[gameData] = gameForm.bindFromRequest()

    newGame.fold(
      hasErrors = {form =>
        print("Problem with game options")
        Redirect(routes.AdminController.create).flashing(Flash(form.data) + ("error" -> Messages("validation.game.errors")))
      },
      success = { newGame =>
        val game: Game = new Game(newGame.player1, newGame.player2, newGame.startTime, newGame.increment)
        val doc: Document = model.Game.gameToDocument(game)
        Game.saveGame(doc)
        Redirect(routes.GameController.play).
          flashing("success" -> Messages("message"))
      }
    )
  }

  /*
@brief form for registering new users
 */
  val gameForm: Form[gameData] = Form(
    mapping(
      "player1" -> text,
      "player2" -> text,
      "startTime" -> number,
      "increment" -> number
    )(gameData.apply)(gameData.unapply)
  )

  def create = Action { implicit request =>

    val form = if (request.flash.get("error").isDefined)
      gameForm.bind(request.flash.data)
    else gameForm

    Ok(views.html.gameOptions(form))
  }


}

