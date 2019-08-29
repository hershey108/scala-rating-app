name := """scala-rating-app"""
organization := "com.ftsl"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

lazy val playground = (project in file("playground")).dependsOn(root)

scalaVersion := "2.12.8"
scalacOptions += "-Ypartial-unification"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test,
  "com.beachape" %% "enumeratum" % "1.5.13",
  "org.scanamo" %% "scanamo" % "1.0.0-M10",
  "org.typelevel" %% "cats-core" % "1.6.0",
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.ftsl.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.ftsl.binders._"
