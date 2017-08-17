package com.trn.rest.models


import io.circe.{ Json, Encoder }

/**
  * Currency case class.
  */
case class Currency(message: String)

/**
  * Currency companion object.
  */
object Currency {

  /**
    * Provides an implicit JSON encoder for the Currency model.
    */
  implicit val encodeJson: Encoder[Currency] =
    Encoder.instance(c => Json.obj("text" -> Json.fromString(c.message)))

}

