package com.trn.rest.services

import com.trn.rest.models.Currency
import org.jsoup.Jsoup
import io.circe.syntax._
import play.api.libs.json._

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

  def fetchFromTransferwise(currency1: String, currency2: String): String = {

    val currencyOne = currency1.toUpperCase()
    val currencyTwo = currency2.toUpperCase()

    Jsoup
      .connect(s"https://transferwise.com/api/v1/payment/calculate?amount=1000&amountCurrency=source&getNoticeMessages=true&hasDiscount=false&isFixedRate=false&isGuaranteedFixedTarget=false&payInMethod=ADYEN_DEBIT&sourceCurrency=$currencyOne&targetCurrency=$currencyTwo")
      .userAgent(
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.153 Safari/537.36")
        .header("x-authorization-key","dad99d7d8e52c2c8aaf9fda788d8acdc") // key might change
        .ignoreContentType(true)
      .get()
      .text()

  }


  def getINRRaw(currency1: String, currency2: String = "inr"): HttpResponse[String] = {

    val currencyToInr: String = fetchFromGoogle(currency1, currency2)
    val currency = Currency(s"1 ${currency1.toUpperCase} is $currencyToInr ${currency2.toUpperCase}")


    postToSlack(currency)

  }

  def parseTransferwiseJson(json: String,currency1: String, currency2: String): String = {
    val jsonValue = Json.parse(json)
    val transferwiseRate = (jsonValue \ "transferwiseRate").as[Double]
    val transferwisePayIn = (jsonValue \ "transferwisePayIn").as[Double]
    val transferwisePayOut = (jsonValue \ "transferwisePayOut").as[Double]

    s"Current Transferwise rate is `$transferwiseRate` \n`$transferwisePayIn $currency1` is giving you `$transferwisePayOut $currency2`!!"

  }

  def getINRTransferwise(currency1: String, currency2: String = "inr"): HttpResponse[String] = {

    val currencyToInr: String = fetchFromTransferwise(currency1, currency2)
    val parsedJson: String = parseTransferwiseJson(currencyToInr, currency1, currency2)
    val currency = Currency(s"${parsedJson}")


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
