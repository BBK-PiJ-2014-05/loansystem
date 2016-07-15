package controllers

import com.google.inject.Inject
import models.Customer
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.mvc.{Action, Controller}
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, number}
import play.api.mvc.Flash

/**
  * Created by geoffreywatson on 06/07/2016.
  */

class Customers @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def list = Action { implicit request =>

    val customers = Customer.findAll

    Ok(views.html.customers.list(customers))

  }

  def show(cid:Int) = Action { implicit request =>

    Customer.findByCid(cid).map { customer =>

    Ok(views.html.customers.details(customer))
    }.getOrElse(NotFound)

  }

  private val customerForm:Form[Customer] = Form(
    mapping(
      "cid" -> number.verifying("validation.cid.duplicate",Customer.findByCid(_).isEmpty),
      "first" -> nonEmptyText,
      "last" -> nonEmptyText,
      "address" -> nonEmptyText

    )(Customer.apply)(Customer.unapply)
  )

  def save = Action { implicit request =>

    val newCustomerForm = customerForm.bindFromRequest()

    newCustomerForm.fold(

      hasErrors = { form =>
        Redirect(routes.Customers.newCustomer()).
          flashing(Flash(form.data) +
            ("error") -> Messages("validation.errors"))
      },

      success = { newCustomer =>
        Customer.add(newCustomer)
        val message = Messages("customers.new.success", newCustomer.last)
        Redirect(routes.Customers.show(newCustomer.cid)).
          flashing("success" -> message)


      }
    )
  }

  def newCustomer = Action { implicit request =>

    val form = if(request.flash.get("error").isDefined)
      customerForm.bind(request.flash.data)
    else
      customerForm

    Ok(views.html.customers.editCustomer(form))

  }

}
