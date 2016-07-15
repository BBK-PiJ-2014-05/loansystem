package controllers

import com.google.inject.Inject
import models.{Customer, User}
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.mvc.{Action, Controller}
import play.api.mvc.Flash

/**
  * Created by geoffreywatson on 07/07/2016.
  */
class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {


  def index = Action { implicit request =>

    Ok(views.html.welcome())

  }

  private val loginForm:Form[User] = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(User.apply)(User.unapply)
  )



  def newLogin = Action { implicit request =>

    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest("Oh noes, invalid submission"),
      value => Ok("created: " + value)
    )

  }

  def hello = Action { implicit request =>
    Ok(views.html.login())
  }

  def newUser = Action { implicit request =>
    Ok(views.html.newUser())
  }


}
