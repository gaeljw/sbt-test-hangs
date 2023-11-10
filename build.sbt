scalaVersion := "2.13.12"

name := "sbt-test-hangs"

lazy val jdkVersion = "11"
scalacOptions += "-release:%s".format(jdkVersion)
javacOptions ++= Seq("-source", jdkVersion, "-target", jdkVersion)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

scalacOptions ++= Seq("-feature", "-deprecation", "-Xfatal-warnings")

Test / unmanagedClasspath += baseDirectory.value / "test/resources"

Compile / doc / sources := Seq.empty
Compile / packageDoc / publishArtifact := false

topLevelDirectory := None

libraryDependencies ++= Seq(
  ws,
  filters,
  guice,

  jdbc,

  // Scalatest
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test,
  "org.scalatestplus" %% "junit-4-13" % "3.2.17.0" % Test,
  "org.scalatestplus" %% "mockito-3-12" % "3.2.10.0" % Test,

  // Pact
  // 0.9.0 works fine, 0.10.0 not compatible with Java 11 anymore
  "io.github.jbwheatley" %% "pact4s-scalatest" % "0.10.0" % Test
)

routesGenerator := InjectedRoutesGenerator
