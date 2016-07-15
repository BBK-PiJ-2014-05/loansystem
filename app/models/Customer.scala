package models

/**
  * Created by geoffreywatson on 06/07/2016.
  */
case class Customer(cid:Int,first:String,last:String,address:String)

object Customer{

  var customers = Set(
    Customer(1000,"Geoff","Watson","25, Ormond House"),
    Customer(1002,"Louise","Dayton","233 Claremont Dr"),
    Customer(1003,"Bjgorvin","Fridrikkson","2 Eyot Nigotterie"),
    Customer(1004,"Peter","Livelier","2330 E Corrine Dr")
  )

  def findAll = customers.toList.sortBy(_.cid)

  def findByCid(cid:Int) = customers.find(_.cid==cid)


  def add(customer:Customer) {
    customers = customers + customer
  }
}
