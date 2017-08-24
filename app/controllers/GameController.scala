package controllers

import javax.inject.{Inject, Singleton}

import play.api.i18n.MessagesApi
import play.api.mvc.{AbstractController, ControllerComponents}
import play.twirl.api.Html
import services.GameService

@Singleton
class GameController @Inject()(cc: ControllerComponents, messagesApi: MessagesApi, Game: GameService) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def play = Action { implicit request =>
    val input: Html = Html.apply("<h1>This is where the game will go</h1>")
    Ok(views.html.gamePlay())
  }



}
