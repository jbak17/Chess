name := """chess"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.194"
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0"
libraryDependencies += "org.webjars" %% "webjars-play" % "2.6.1"
libraryDependencies += "org.webjars.bower" % "react" % "15.6.1"
