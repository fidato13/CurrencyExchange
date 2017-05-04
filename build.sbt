name := """CurrencyExchange"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "org.jsoup" % "jsoup" % "1.8.1"
// https://mvnrepository.com/artifact/commons-codec/commons-codec
libraryDependencies += "commons-codec" % "commons-codec" % "1.10"
// https://mvnrepository.com/artifact/org.apache.commons/commons-email
libraryDependencies += "org.apache.commons" % "commons-email" % "1.1"
// https://mvnrepository.com/artifact/com.twilio.sdk/twilio-java-sdk
libraryDependencies += "com.twilio.sdk" % "twilio-java-sdk" % "6.3.0"


// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

