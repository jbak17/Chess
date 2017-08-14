package controllers

import javax.inject._

import play.api.data.Form
import play.api.data.Forms._

import play.api.mvc._
import play.api.mvc.Flash
import play.api.i18n._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, messagesApi: MessagesApi) extends AbstractController(cc) with play.api.i18n.I18nSupport  {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    Redirect(routes.UserController.list())
  }

}
