package controllers

import services._
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._

import play.api.mvc._
import play.api.mvc.Flash
import play.api.i18n._

@Singleton
class UserController @Inject()(cc: ControllerComponents, messagesApi: MessagesApi) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def list = Action { implicit request =>
    val users: List[User] = User.listUsers
    Ok(views.html.Users.details(users))
  }

  /*
  @brief used to show a single user
 */
  def show(email: String) = Action { implicit request =>
    val user: Option[User] = Option(User.getUserByEmail(email).head)
    user.map { u =>
      Ok(views.html.Users.details(List(u)))
    }.getOrElse(NotFound)
  }

  /*
  @brief form for registering new users
   */
  private val newUserForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> email.verifying(
        "validation.email.duplicate", User.getUserByEmail(_).isEmpty)
    )(NU.apply)(NU.unapply)
  )

  /*
  @brief renders the user registration form
   */
  def registerUser = Action { implicit request =>
    val form = if (request.flash.get("error").isDefined)
      newUserForm.bind(request.flash.data)
    else newUserForm

    Ok(views.html.Users.register(form))
  }

  /*
  Saves new user to storage, or redirects with error
   */
  def save = Action { implicit request =>
    val newUser = newUserForm.bindFromRequest()

    newUser.fold(
      hasErrors = {form =>
        Redirect(routes.UserController.registerUser).flashing(Flash(form.data) + ("error" -> Messages("validation.errors")))
      },
      success = { newUser =>
        val user: User = User(newUser.name, newUser.email)
        User.createUser(user)
        Redirect(routes.UserController.list).
          flashing("success" -> Messages("message"))
      }
    )
  }

}
