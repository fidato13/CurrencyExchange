package com.trn.sms

import com.twilio.sdk.TwilioRestClient
import com.twilio.sdk.TwilioRestException
import com.twilio.sdk.resource.factory.MessageFactory
import com.twilio.sdk.resource.instance.Message
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair

import java.util.ArrayList
import java.util.List

object SendTwilio /*extends App*/ {

  val ACCOUNT_SID = "AC270965dc1d10bbe8aa812f944c351e2c"
  val AUTH_TOKEN = "57a39ded3bd4b43913fb47703ad5cd65"

  val client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN)

  // Build a filter for the MessageList
  val params = new ArrayList[NameValuePair]()
  params.add(new BasicNameValuePair("Body", "Test Twilio messagessssss"))
  params.add(new BasicNameValuePair("To", "+447441907695"))
  params.add(new BasicNameValuePair("From", "+441618504732"))

  val messageFactory = client.getAccount().getMessageFactory()
  val message = messageFactory.create(params)

  println(message.getSid())

}
