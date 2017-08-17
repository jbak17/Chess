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

import services.GameService
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.MessagesApi
import play.api.mvc.{AbstractController, ControllerComponents}
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

    Ok(views.html.index("Your new application is ready."))

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

