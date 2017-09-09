package com.trn.rest.services

import com.trn.rest.services.Calculation._

object QuickTest /*extends App*/ {

  val responseFromTransferWise = getINRTransferwise("GBP","INR")

  println(s"The response from transferwise => $responseFromTransferWise")
}
