package com.trn.sms

import javax.mail._
import javax.mail.internet._
import java.util.Properties

object SendMail extends App {


  val username = "<enter senders valid email>"
  val password = "<enter your password here>"
  val toAddress = "<enter recipient address here>"

  val props = new Properties()
  props.put("mail.smtp.auth", "true")
  props.put("mail.smtp.starttls.enable", "true")
  props.put("mail.smtp.host", "smtp.gmail.com")
  props.put("mail.smtp.port", "587")

  val session = Session.getInstance(props,
    new javax.mail.Authenticator() {
     override def getPasswordAuthentication(): PasswordAuthentication = {
        return new PasswordAuthentication(username, password)
      }
    })

  try {

    val message = new MimeMessage(session)
    message.setFrom(new InternetAddress("from-email@gmail.com"))
    message.addRecipient(Message.RecipientType.TO, new
      InternetAddress(toAddress))
    message.setSubject("Testing Subject")
    message.setText("Dear Mail Crawler,"
      + "\n\n No spam to my email, please!")

    Transport.send(message)

    println("Done")

  } catch  {
    case ex =>  ex.printStackTrace()
  }

}
