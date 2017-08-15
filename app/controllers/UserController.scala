package controllers

import services._
import model._
import javax.inject._

import play.api.data.Form
import play.api.data.Forms._

import play.api.mvc._
import play.api.mvc.Flash
import play.api.i18n._

@Singleton
class UserController @Inject()(cc: ControllerComponents, messagesApi: MessagesApi, userService: UserService) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def list = Action { implicit request =>
    val users: List[UserInstance] = userService.listUsers
    Ok(views.html.Users.userList(users))
  }

  /*
  @brief used to show a single user
 */
  def show(email: String) = Action { implicit request =>
    val user: UserInstance = userService.getUserByEmail(email)
    if(user.isInstanceOf[UserInstance]) {
      Ok(views.html.Users.details(user))
    } else
      Ok(views.html.Users.notFound())

  }

  /*
  @brief form for registering new users
   */
  private val newUserForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> email//.verifying(
        //"validation.email.duplicate", email => userService.userAlreadyExists(email))
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
        print("Problem with user form")
        Redirect(routes.UserController.registerUser).flashing(Flash(form.data) + ("error" -> Messages("validation.user.errors")))
      },
      success = { newUser =>
        val user: UserInstance = UserInstance(newUser.name, newUser.email)
        userService.saveUser(user)
        Redirect(routes.UserController.list).
          flashing("success" -> Messages("message"))
      }
    )
  }

}
