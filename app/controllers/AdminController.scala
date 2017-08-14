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

import org.joda.time.DateTime
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.MessagesApi
import play.api.mvc.{AbstractController, ControllerComponents}
import services.{Game, User}

case class gameData(player1: String, player2: String, startTime: Int, increment: Int)

@Singleton
class AdminController @Inject()(cc: ControllerComponents, messagesApi: MessagesApi) extends AbstractController(cc) with play.api.i18n.I18nSupport {

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

