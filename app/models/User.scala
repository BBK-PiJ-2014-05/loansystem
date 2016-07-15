package models

/**
  * Created by geoffreywatson on 11/07/2016.
  */
case class User(email:String,password:String){
  var loggedIn = false
  def validatePassword(psswd:String): Boolean ={
    password == psswd
  }
}

case object User{

  var users = Set(

    User("gwatson","tea103"),
    User("bhughes","cdfa")

  )

  def addUser(user:User) = {

    users = users + user
  }

  def deleteUser(user:User) = {

    users = users - user
  }

  def userExists(email:String):Boolean ={

    users.map(_.email).contains(email)

  }

  def getUser(email:String):Option[User] = {
    val user = users.find(_.email.equals(email))
    user
  }



  def userLogIn(email:String,password:String): Boolean = {

    val user = getUser(email).getOrElse(null)

    if(user.validatePassword(password)) {
      user.loggedIn= true
    }
    user.loggedIn
  }

  def userLogOut(email:String): Boolean ={

    val user = getUser(email).getOrElse(null)

    user.loggedIn = false

    user.loggedIn

  }



}