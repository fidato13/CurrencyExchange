package com.trn.rest.endpoint

import com.trn.rest.models.Currency
import io.circe.{Encoder, Json}
import io.finch._
import io.finch.circe._
import com.trn.rest.services.Calculation._


object Endpoints {

  val pathRaw = "raw"
  val pathTransfer = "transfer"
  val pathINR = "INR"
  val pathGBP = "GBP"
  val pathEUR = "EUR"


  // Route for
  //private val getINRRAW = get(path :: string :: int :: paramOption("title")) {
   val getGBPToINRRAW = get(pathRaw :: pathGBP :: pathINR) {
    getINRRaw(pathGBP.toLowerCase)
    Ok("Posted to Slack")

  }

  val getEURToINRRAW = get(pathRaw :: pathEUR :: pathINR) {
    getINRRaw(pathEUR.toLowerCase)
    Ok("Posted to Slack")
  }

   val getINRTransfer = get(pathTransfer :: pathGBP :: pathINR) {
    Ok("Test-INR-Transfer")
  }

  // Endpoints
  val combined = getGBPToINRRAW :+: getEURToINRRAW :+: getINRTransfer

  /**
    * sample Endpoint :
    * http://localhost:7070/raw/GBP/INR
    */

  // Convert domain errors to JSON
  implicit val encodeException: Encoder[Exception] = Encoder.instance { e =>
    Json.obj(
      "type" -> Json.fromString(e.getClass.getSimpleName),
      "error" -> Json.fromString(Option(e.getMessage).getOrElse("Internal Server Error"))
    )
  }

  /**
    * Greeting API
    */
  val currencyAPI = combined.handle {
    case e: IllegalArgumentException =>
      BadRequest(e)
    case t: Throwable =>
      InternalServerError(new Exception(t.getCause))
  }.toServiceAs[Text.Plain]

}
