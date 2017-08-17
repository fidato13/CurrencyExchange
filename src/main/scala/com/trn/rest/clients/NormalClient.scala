package com.trn.rest.clients

import scalaj.http.{Http, HttpOptions, HttpResponse}

object NormalClient {




  val currentRate: String = Http("http://localhost:7070/raw/GBP/INR").asString.body

  println(s"Current Rate $currentRate")

  //post to slack
  Http("https://hooks.slack.com/services/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").postData(currentRate)
    .header("Content-Type", "application/text")
    .header("Charset", "UTF-8")
    .option(HttpOptions.readTimeout(10000)).asString




}
