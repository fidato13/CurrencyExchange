package com.trn.rest.services

import com.trn.rest.models.Currency
import org.jsoup.Jsoup
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scalaj.http.{Http, HttpOptions, HttpResponse}


object Calculation {

  def fetchFromGoogle(currency1: String, currency2: String): String = {
    Jsoup
      .connect(s"https://www.google.com/search?q=$currency1+to+$currency2")
      .userAgent(
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.153 Safari/537.36")
      .get()
      .getElementsByClass("vk_ans")
      .first()
      .text()

  }


  def getINRRaw(currency1: String, currency2: String = "inr"): HttpResponse[String] = {

    val gbpToInr: String = fetchFromGoogle(currency1, currency2)
    val currency = Currency(s"1 ${currency1.toUpperCase} is $gbpToInr ${currency2.toUpperCase}")


    postToSlack(currency)

  }

  def postToSlack(currency: Currency) = {

    //post to slack
    Http("https://hooks.slack.com/services/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").postData(currency.asJson.noSpaces)
      .header("Content-Type", "application/text")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000)).asString
  }

}
