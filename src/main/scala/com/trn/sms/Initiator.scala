package com.trn.sms

import org.jsoup.Jsoup

object Initiator extends App {

  val currency1 = "eur"
  val currency2 = "inr"
  val response = Jsoup
    .connect(s"https://www.google.com/search?q=$currency1+to+$currency2")
    .userAgent(
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.153 Safari/537.36")
    .get()

  val elements = response.getElementsByClass("vk_ans").first().text()

  println(s"1 Euro = $elements")


}
